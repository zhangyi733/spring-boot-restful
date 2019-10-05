package com.restful.model.error;

public class ValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ValidationError() {
        this((String)null, (String)null);
    }

    public ValidationError(String object, String message) {
        this(object, (String)null, (Object)null, message);
    }

    public ValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return this.object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return this.rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

