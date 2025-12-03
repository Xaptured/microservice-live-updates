package com.esportslan.microservices.live_updates.utilities;

import com.esportslan.microservices.live_updates.exceptions.BadRequestErrorException;
import com.esportslan.microservices.live_updates.exceptions.InternalErrorException;
import com.esportslan.microservices.live_updates.models.ExceptionBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionBody exceptionBody = null;
        try {
            if (response.body() != null) {
                exceptionBody = objectMapper.readValue(response.body().asInputStream(), ExceptionBody.class);
            }
        } catch (IOException exception) {
            throw new InternalErrorException("Error while parsing exceptionBody from client", exception);
        }
        switch (response.status()) {
            case 400:
                return new BadRequestErrorException(exceptionBody != null ? exceptionBody.getMessage() : "Bad Request");
            case 404:
                return new BadRequestErrorException(exceptionBody != null ? exceptionBody.getMessage() : "Not Found");
            case 500:
                return new InternalErrorException(exceptionBody != null ? exceptionBody.getMessage() : "Internal Error");
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
