package com.dyy.tsp.common.exception;

@SuppressWarnings("all")
public enum BaseError implements IError {
    SYSTEM_INTERNAL_ERROR("001", "服务器内部错误"),
    ;
    String errorCode;
    String errorMessage;

    BaseError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getNamespace() {
        return null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
