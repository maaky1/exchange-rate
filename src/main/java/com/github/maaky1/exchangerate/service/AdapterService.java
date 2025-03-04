package com.github.maaky1.exchangerate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class AdapterService {

    public ResponseEntity<String> call(
            String requestId,
            String action,
            String url,
            HttpMethod method,
            HttpHeaders headers,
            Object body,
            int timeout
    ) {
        log.info("==========================[Request Begin]===============================================");
        log.info("ID           : {}", requestId);
        log.info("Action       : {}", action);
        log.info("URL          : {}", url);
        log.info("Method       : {}", method);
        log.info("Headers      : {}", headers);
        log.info("Request body : {}", body);
        log.info("Timeout      : {}", timeout);
        log.info("=========================[Request End]=================================================");

        Date startCall = Calendar.getInstance().getTime();
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;

        try {
            response = getRestTemplate(timeout).exchange(url, method, request, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
        } catch (Exception e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("UnknownError", "true");
            response = new ResponseEntity<>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("===========================[Response Begin]=========================================");
        log.info("ID           : {}", requestId);
        log.info("Action       : {}", action);
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", response.getBody());
        log.info("Time         : {} ms", Calendar.getInstance().getTime().getTime() - startCall.getTime());
        log.info("===========================[Response End]============================================");

        return response;
    }

    private RestTemplate getRestTemplate(int timeout) {
        SimpleClientHttpRequestFactory simpleFactory = new SimpleClientHttpRequestFactory();
        simpleFactory.setConnectTimeout(timeout);
        simpleFactory.setReadTimeout(timeout);
        return new RestTemplate(simpleFactory);
    }
}
