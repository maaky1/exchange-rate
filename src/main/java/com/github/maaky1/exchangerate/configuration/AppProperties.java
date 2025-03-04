package com.github.maaky1.exchangerate.configuration;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties {

    private final String BASE_URL = "https://open.er-api.com/v6";
    private final String URL_CURRENCY = "/latest/";
}
