package com.tourism.util.validator;

public class PositiveIntegerValidator implements Validator<Integer> {
    private final String message;

    public PositiveIntegerValidator(String message) {
        this.message = message;
    }

    @Override
    public Result validate(Integer value) {
        if (value > 0) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}
