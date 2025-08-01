package org.galatea.starter.service;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * A configuration for injecting the API key into Finnhub requests
 */
@Configuration
@RequiredArgsConstructor
public class FinnhubClientConfig {

    @Value("${finnhub.apiKey}")
    private String apiKey;

    @Bean
    public RequestInterceptor finnhubApiKeyInterceptor() {
        return requestTemplate -> requestTemplate.query("token", apiKey);
    }
}
