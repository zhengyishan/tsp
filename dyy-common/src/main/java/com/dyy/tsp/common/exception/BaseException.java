package com.dyy.tsp.common.exception;

@SuppressWarnings("all")
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -6293662498600553602L;
    private IError error = BaseError.SYSTEM_INTERNAL_ERROR;
    private String extMessage = null;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
        this.extMessage = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.extMessage = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
        if (cause instanceof BaseException) {
            BaseException fe = (BaseException) cause;
            this.error = fe.getError();
            this.extMessage = fe.getMessage();
        }
    }

    public BaseException(IError error) {
        super(error.getErrorCode() + ":" + error.getErrorMessage());
        this.error = error;
    }

    public BaseException(IError error, String message) {
        super(error.getErrorCode() + ":" + error.getErrorMessage());
        this.error = error;
        this.extMessage = message;
    }

    public BaseException(Throwable cause, IError error, String message) {
        super(message, cause);
        this.extMessage = message;
        this.error = error;
    }

    public BaseException(IError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public IError getError() {
        return error;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String toString() {
        return super.toString() + ",ErrorCode : " + error.getErrorCode() + ", ErrorMessage : " + error.getErrorMessage() + ", ExtMessage : " + extMessage;
    }
}
