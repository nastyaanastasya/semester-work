package ru.kpfu.exceptions;

public class UnknownEmailException extends RuntimeException{

    public UnknownEmailException() {
        super();
    }

    public UnknownEmailException(String message) {
        super(message);
    }

    public UnknownEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEmailException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
