package com.tnf.productlist.util;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CurrencyExchangeUtil {

	private static final String CURRENCY_GBP = "GBP";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Value("${fixerio.url}")
    private String fixerIOUrl;
    
    @Value("${fixerio.apiaccesskey}")
    private String fixerIOApiAccessKey;

    public double convert(String from, String to, double amount) {
        if (fixerIOApiAccessKey == null
            || fixerIOApiAccessKey.isEmpty()
            || "${fixerio.apiaccesskey}".equals(fixerIOApiAccessKey)) {
            throw new IllegalArgumentException("FixerIO Api Access Key is not set");
        }

        if (!CURRENCY_GBP.equals(to)) {
            throw new IllegalArgumentException("Currently only supported conversion is " + CURRENCY_GBP);
        }

        final FixerIOResponse fixerIOResponse =
            REST_TEMPLATE.getForEntity(URI.create(fixerIOUrl + fixerIOApiAccessKey  ), FixerIOResponse.class).getBody();

        if (fixerIOResponse.getRates() == null) {
            throw new IllegalStateException("No rates were retrieved from Fixer IO");
        }

        return amount * (1.0 / fixerIOResponse.getRates().get(from));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FixerIOResponse {
        private Map<String, Double> rates;

        public Map<String, Double> getRates() {
            return rates;
        }

    }
}
