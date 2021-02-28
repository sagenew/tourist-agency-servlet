package com.tourism.util.validator;

public interface Validator<T> {
    Result validate(T value);
}
