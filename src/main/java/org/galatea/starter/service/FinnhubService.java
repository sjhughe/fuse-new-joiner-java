package org.galatea.starter.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import feign.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.FinnhubQuote;
import org.galatea.starter.domain.FinnhubQuoteEntity;
import org.galatea.starter.domain.FinnhubSymbol;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from Finnhub.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FinnhubService {

    @NonNull
    private FinnhubClient finnhubClient;

    private final IFinnhubQuoteRepository repository;
    private final Duration CACHE_TTL = Duration.ofMinutes(5);


    /**
     * Get all stock symbols from Finnhub.
     *
     * @return a list of all Stock Symbols from Finnhub.
     */
    public List<FinnhubSymbol> getAllSymbols() {
        return finnhubClient.getAllSymbols();
    }

    public FinnhubQuote getQuote(String symbol) {

        FinnhubQuoteEntity cached = repository.findById(symbol).orElse(null);
        FinnhubQuote apiQuote = null;

        //1. Returned cached data if not stale
        if (cached != null && Instant.now().isBefore(cached.getCachedAt().plus(CACHE_TTL))) {
            log.info("Retrieved quote from cache: {}", cached.getQuote());
            return cached.getQuote();
        }

        try {
            //2. Try API
            apiQuote = finnhubClient.getQuote(symbol);

            //Finnhub will return dummy data with all 0s if the symbol is not found, we want to flag that
            boolean allZero = apiQuote.getCurrent().compareTo(BigDecimal.ZERO) == 0 &&
                    apiQuote.getPreviousClose().compareTo(BigDecimal.ZERO) == 0 &&
                    apiQuote.getHigh().compareTo(BigDecimal.ZERO) == 0 &&
                    apiQuote.getLow().compareTo(BigDecimal.ZERO) == 0 &&
                    apiQuote.getOpen().compareTo(BigDecimal.ZERO) == 0;

            //3. Ensure data received is legit
            if(allZero) {
                log.error("Finnhub returned no data for symbol: " + symbol);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Finnhub returned no data for symbol: " + symbol
                );
            }

            log.info("Retrieved quote from Finnhub: {}", apiQuote);
        } catch (ResponseStatusException e) {
            //Pass through known symbol error
            throw e;
        } catch (Exception e) {
            //4. API didn't work. Throw error
            log.error("Failed to retrieve quote from Finnhub", e);
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Failed to retrieve quote from Finnhub for " + symbol, e
            );
        }


        FinnhubQuoteEntity entity = FinnhubQuoteEntity.builder()
                .symbolID(symbol)
                .cachedAt(Instant.now())
                .quote(apiQuote)
                .build();

        repository.save(entity);
        return apiQuote;
    }
}
