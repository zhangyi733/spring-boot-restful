package com.restful.exception;

import com.restful.model.error.CustomError;
import com.restful.model.error.ValidationError;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleExceptionNotFound(final NoSuchElementException ex) {
        logger.debug("could not find page", ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleExceptionBadRequest(final IllegalArgumentException ex) {
        logger.debug("Invalid argument", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleBadRequests(final Exception ex) {
        logger.debug("Bad input from client", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError processConstraintError(DataIntegrityViolationException ex) {
        logger.debug("Database constraint violation", ex);
        CustomError errors = new CustomError(HttpStatus.BAD_REQUEST, "Validation Error");
        String message = ex.getMostSpecificCause().getMessage();
        List<ValidationError> subErrors = new ArrayList<>();
        if (message.contains("not-null constraint")) {
            subErrors.add(new ValidationError("Not Null violation", "body miss some not-null value"));
        }
        errors.setSubErrors(subErrors);
        return errors;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        CustomError errors = new CustomError(HttpStatus.BAD_REQUEST, "Validation Error");
        List<ValidationError> subErrors = new ArrayList<>();
        if (ex.getBindingResult().getFieldErrorCount() > 0) {
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                subErrors.add(new ValidationError(fieldError.getObjectName().replace("Dto", ""), fieldError.getField(),
                    fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
            }
        }
        errors.setSubErrors(subErrors);
        return errors;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDenied(AccessDeniedException ex) {
        logger.debug("Access Denied", ex);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomError handleObjectVersionDifferent(ObjectOptimisticLockingFailureException ex) {
        logger.debug("Conflict", ex);
        return new CustomError(HttpStatus.CONFLICT, "Asset has been modified by another request");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CustomError handleExceptionMethodNotAllowed(final Exception ex) {
        logger.debug("Method not allowed", ex);
        return new CustomError(HttpStatus.METHOD_NOT_ALLOWED, "HTTP Method not allowed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomError handleException(HttpServletRequest httpServletRequest, Exception ex) {
        logger.error("Unknown exception [queryString=\"{}\" errorMessage=\"{}\"]", httpServletRequest.getQueryString(), ex.getMessage(), ex);
        return new CustomError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Oops something went wrong. Please contact us if this keeps occurring.");
    }
}
