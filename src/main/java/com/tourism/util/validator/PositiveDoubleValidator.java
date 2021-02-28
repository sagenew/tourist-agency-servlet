package com.tourism.util.validator;

public class PositiveDoubleValidator implements Validator<Double> {
    private final String message;

    public PositiveDoubleValidator(String message) {
        this.message = message;
    }

    @Override
    public Result validate(Double value) {
        if (value > 0.0) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}

