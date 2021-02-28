package com.tourism.util.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeValidator<T> {
    private final List<Validator<T>> validatorList = new ArrayList<>();

    @SafeVarargs
    public CompositeValidator(Validator<T>... validators) {
        this.validatorList.addAll(Arrays.asList(validators));
    }

    public Result validate(T value) {
        CompositeResult result = new CompositeResult();
        validatorList.stream()
                .map(validator -> validator.validate(value))
                .forEach(result::addResult);
        return result;
    }
}