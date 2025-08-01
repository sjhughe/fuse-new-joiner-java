package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FinnhubQuote {

    @JsonProperty("c")
    private BigDecimal current;
    @JsonProperty("pc")
    private BigDecimal previousClose;
    @JsonProperty("h")
    private BigDecimal high;
    @JsonProperty("l")
    private BigDecimal low;
    @JsonProperty("o")
    private BigDecimal open;
    private String symbol;
}
