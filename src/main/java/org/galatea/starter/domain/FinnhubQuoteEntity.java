package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "finnhub_quote_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinnhubQuoteEntity {
    @Id
    private String symbolID;

    @Embedded
    private FinnhubQuote quote;

    private Instant cachedAt;
}
