package com.github.maaky1.exchangerate.usecase;

import com.github.maaky1.exchangerate.dto.ApiRs;
import com.github.maaky1.exchangerate.model.ExchangeRateRs;
import com.github.maaky1.exchangerate.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainUsecase {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public MainUsecase(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public ApiRs getExchangeRates(String baseCurrency) {
        ApiRs apiRs = new ApiRs();
        ExchangeRateRs exchangeRates = exchangeRateService.getExchangeRates(baseCurrency);
        if (exchangeRates != null && exchangeRates.getResult().equalsIgnoreCase("success")) {
            apiRs.setSuccess(exchangeRates);
        } else {
            apiRs.setError("01", exchangeRates.getResult(), exchangeRates.getError());
        }
        return apiRs;
    }
}
