package org.galatea.starter.entrypoint;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import net.sf.aspect4log.Log.Level;
import org.galatea.starter.domain.FinnhubQuote;
import org.galatea.starter.domain.FinnhubSymbol;
import org.galatea.starter.service.FinnhubService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Log(enterLevel = Level.INFO, exitLevel = Level.INFO)
@Validated
@RestController
@RequiredArgsConstructor
public class FinnhubRestController {

  @NonNull
  private FinnhubService finnhubService;

  /**
   * Exposes an endpoint to get all of the symbols available on Finnhub.
   *
   * @return a list of all FinnhubSymbols.
   */
  @GetMapping(value = "${mvc.finnhub.getAllSymbolsPath}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<FinnhubSymbol> getAllStockSymbols() {
    return finnhubService.getAllSymbols();
  }

  @GetMapping(value = "${mvc.finnhub.getQuotePath}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public FinnhubQuote getQuote(@RequestParam(value = "symbol") final String symbol) {
    FinnhubQuote quote = finnhubService.getQuote(symbol);
    quote.setSymbol(symbol);
    return quote;
  }

}
