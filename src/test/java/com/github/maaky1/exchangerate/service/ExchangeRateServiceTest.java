package com.github.maaky1.exchangerate.service;

import com.github.maaky1.exchangerate.exception.CommonException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void getExchangeRate() {
        exchangeRateService.getExchangeRates("USD");
    }
}