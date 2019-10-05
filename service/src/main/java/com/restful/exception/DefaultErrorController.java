package com.restful.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    private static final String path = "/error";

    @RequestMapping(value = path)
    public ResponseEntity<String> error() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}
