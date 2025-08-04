package org.galatea.starter.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.FinnhubQuote;
import org.galatea.starter.domain.FinnhubQuoteEntity;
import org.galatea.starter.domain.FinnhubSymbol;
import org.springframework.stereotype.Service;

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

        if (cached != null && cached.getCachedAt().isBefore(cached.getCachedAt().plus(CACHE_TTL))) {
            return cached.getQuote();
        }

        FinnhubQuote apiQuote = finnhubClient.getQuote(symbol);

        FinnhubQuoteEntity entity = FinnhubQuoteEntity.builder()
                .symbolID(symbol)
                .cachedAt(Instant.now())
                .quote(apiQuote)
                .build();

        repository.save(entity);
        return apiQuote;
    }
}
