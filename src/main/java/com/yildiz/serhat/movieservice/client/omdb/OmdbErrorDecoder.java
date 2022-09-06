package com.yildiz.serhat.movieservice.client.omdb;

import com.yildiz.serhat.movieservice.exception.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static java.util.Objects.nonNull;

@Slf4j
public class OmdbErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = response.body().toString();
            logError(responseBody, response);

            return new FeignClientException("Omdb Feign client exception has been occurred.", HttpStatus.valueOf(response.status()));
        } catch (Exception e) {
            return new FeignClientException("Order Feign client exception has been occurred.", HttpStatus.valueOf(response.status()));
        }
    }

    protected void logError(String responseJson, Response response) {
        if (log.isErrorEnabled()) {
            String requestBody = null;
            if (nonNull(response.request().body())) {
                requestBody = new String(response.request().body(), response.request().charset());
            }
            StringBuilder builder = new StringBuilder();
            builder.append("Http method: ").append(response.request().httpMethod().toString()).append(", ");
            builder.append("Url: ").append(response.request().url()).append(", ");
            builder.append("Headers: ").append(response.request().headers()).append(", ");
            builder.append("Request Body: ").append(requestBody).append(", ");
            builder.append("Status: ").append(response.status()).append(", ");
            builder.append("Response Body: ").append(responseJson);

            log.error(builder.toString());
        }
    }


}
