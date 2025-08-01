package org.galatea.starter.service;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    /**
     * Get all stock symbols from Finnhub.
     *
     * @return a list of all Stock Symbols from Finnhub.
     */
    public List<FinnhubSymbol> getAllSymbols() {
        return finnhubClient.getAllSymbols();
    }
}
