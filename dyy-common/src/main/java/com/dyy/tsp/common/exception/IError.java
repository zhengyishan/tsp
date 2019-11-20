package com.dyy.tsp.common.exception;

@SuppressWarnings("all")
public interface IError {

    String getNamespace();

    String getErrorCode();

    String getErrorMessage();
}
