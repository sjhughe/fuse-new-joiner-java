package org.galatea.starter.service;



import org.galatea.starter.domain.FinnhubSymbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * A Feign Declarative REST Client to access endpoints from finnhub.io to get market
 * data. See https://finnhub.io/docs/api
 */
@FeignClient(
        name = "Finnhub",
        url = "${spring.rest.finnhubBasePath}",
        configuration = FinnhubClientConfig.class)
public interface FinnhubClient {

  /**
   * Get a list of all stocks supported by Polygon. See https://finnhub.io/docs/api/stock-symbols
   * As of July 2019 this returns almost 9,000 symbols, so maybe don't call it in a loop.
   *
   * @return a list of all of the stock symbols supported by Finnhub.
   */
  @GetMapping("/stock/symbol?exchange=US")
  List<FinnhubSymbol> getAllSymbols();

}
