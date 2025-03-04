package com.github.maaky1.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRs<T> {

    private String code;
    private String status;
    private String message;
    private T data;
}
