package com.github.maaky1.exchangerate.controller;

import com.github.maaky1.exchangerate.dto.ApiRs;
import com.github.maaky1.exchangerate.model.ExchangeRateRs;
import com.github.maaky1.exchangerate.service.ExchangeRateService;
import com.github.maaky1.exchangerate.usecase.MainUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Test {

    private final ExchangeRateService exchangeRateService;
    private final MainUsecase mainUsecase;

    @Autowired
    public Test(ExchangeRateService exchangeRateService, MainUsecase mainUsecase) {
        this.exchangeRateService = exchangeRateService;
        this.mainUsecase = mainUsecase;
    }

    @GetMapping("/rates/{baseCurrency}")
    public ResponseEntity<?> getRates(@PathVariable String baseCurrency) {
        ApiRs response = mainUsecase.getExchangeRates(baseCurrency);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getBody());
    }

    @DeleteMapping("/rates/{baseCurrency}")
    public String clearCache(@PathVariable String baseCurrency) {
        exchangeRateService.clearCache(baseCurrency);
        return "Cache Cleared for " + baseCurrency;
    }
}
