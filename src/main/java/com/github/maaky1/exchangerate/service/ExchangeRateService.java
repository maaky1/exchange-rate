package com.github.maaky1.exchangerate.service;

import com.github.maaky1.exchangerate.configuration.AppProperties;
import com.github.maaky1.exchangerate.model.ExchangeRateRs;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ExchangeRateService {

    private final AdapterService adapterService;
    private final AppProperties appProperties;
    private final Gson gson;

    @Autowired
    public ExchangeRateService(AdapterService adapterService, AppProperties appProperties) {
        this.adapterService = adapterService;
        this.appProperties = appProperties;
        this.gson = new GsonBuilder().create();
    }

    @Cacheable(value = "exchangeRates", key = "#baseCurrency")
    public ExchangeRateRs getExchangeRates(String baseCurrency) {
        log.info("Fetching data from API for currency: {}", baseCurrency);

        ExchangeRateRs exchangeRates = new ExchangeRateRs();

        String url = appProperties.getBASE_URL() + appProperties.getURL_CURRENCY() + baseCurrency;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");

        ResponseEntity<String> response = adapterService.call(
                UUID.randomUUID().toString(),
                "get-exchange-rate-" + baseCurrency.toLowerCase(),
                url,
                HttpMethod.GET,
                headers,
                null,
                1
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            return exchangeRates
                    .setResult(response.getStatusCode().is5xxServerError() ? "Error" : "Failed")
                    .setError(response.getBody());
        }

        exchangeRates = gson.fromJson(response.getBody(), ExchangeRateRs.class);
        if (exchangeRates.getResult().equalsIgnoreCase("error")) {
            return exchangeRates
                    .setResult("Failed")
                    .setError(exchangeRates.getError());
        }

        log.info("Saved data currency {} to memory cache", baseCurrency);

        return exchangeRates;
    }

    @CacheEvict(value = "exchangeRates", key = "#baseCurrency")
    public void clearCache(String baseCurrency) {
        log.info("Cache cleared from memory for currency: {}", baseCurrency);
    }
}
