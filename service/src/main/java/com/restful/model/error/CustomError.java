package com.restful.model.error;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;

public class CustomError {

    private HttpStatus httpStatus;
    private String message;
    private Instant timestamp;
    private List<ValidationError> subErrors;

    public CustomError() {
        this.timestamp = Instant.now();
    }

    public CustomError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public CustomError setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CustomError setMessage(String message) {
        this.message = message;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public CustomError setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public List<ValidationError> getSubErrors() {
        return subErrors;
    }

    public CustomError setSubErrors(List<ValidationError> subErrors) {
        this.subErrors = subErrors;
        return this;
    }
}
