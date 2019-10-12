package com.udacity.pricing.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PriceException extends Exception {

    public PriceException(String message) {
        super(message);
    }
}

