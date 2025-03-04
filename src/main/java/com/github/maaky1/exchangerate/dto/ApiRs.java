package com.github.maaky1.exchangerate.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class ApiRs<T> {

    private final GenericRs body = new GenericRs();
    private HttpStatus httpStatus;

    public void setSuccess(T data) {
        this.httpStatus = HttpStatus.OK;
        body.setCode("00");
        body.setStatus("Ok");
        body.setMessage("Success");
        body.setData(data);
    }

    public void setError(String code, String status, String message) {
        this.httpStatus = status.equalsIgnoreCase("Failed") ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        body.setCode(code);
        body.setStatus(status.equalsIgnoreCase("Failed") ? "SystemError" : "BusinessError");
        body.setMessage(message);
    }
}
