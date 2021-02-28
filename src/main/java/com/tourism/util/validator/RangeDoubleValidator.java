package com.tourism.util.validator;

public class RangeDoubleValidator implements Validator<Double> {
    private final double minValue;
    private final double maxValue;
    private final String message;

    public RangeDoubleValidator(double minValue, double maxValue, String message) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.message = message;
    }

    @Override
    public Result validate(Double value) {
        if (value >= minValue && value <= maxValue) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}