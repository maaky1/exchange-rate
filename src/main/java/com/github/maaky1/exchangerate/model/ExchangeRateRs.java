package com.github.maaky1.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateRs {

    private String result;
    @SerializedName("error-type")
    private String error;
    @SerializedName("base_code")
    private String currency;
    private Map<String, Double> rates;
}