package com.sychev.gateway.exception;

import com.sychev.gateway.exception.model.Service;
import org.springframework.http.HttpStatus;

public class GatewayApiException extends RuntimeException {

    private static final long serialVersionUID = -4002602497136732221L;

    private Service service;
    private HttpStatus httpStatus;

    public GatewayApiException(Service service, HttpStatus httpStatus) {
        super();
        this.service = service;
        this.httpStatus = httpStatus;
    }

    public GatewayApiException(Service service, HttpStatus httpStatus, String message) {
        super(message);
        this.service = service;
        this.httpStatus = httpStatus;
    }

    public GatewayApiException(Service service, HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.service = service;
        this.httpStatus = httpStatus;
    }

    public GatewayApiException(Service service, HttpStatus httpStatus,
                               String message, Throwable cause) {
        super(message, cause);
        this.service = service;
        this.httpStatus = httpStatus;
    }

    public Service getService() {
        return service;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}