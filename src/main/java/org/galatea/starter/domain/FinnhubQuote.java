package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FinnhubQuote {
    private String symbol;

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

}
