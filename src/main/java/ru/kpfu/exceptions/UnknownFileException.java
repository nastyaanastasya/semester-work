package ru.kpfu.exceptions;

public class UnknownFileException extends RuntimeException{

    public UnknownFileException() {
    }

    public UnknownFileException(String message) {
        super(message);
    }

    public UnknownFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownFileException(Throwable cause) {
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
