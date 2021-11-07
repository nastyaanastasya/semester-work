package ru.kpfu.exceptions;

public class UnknownAlgorithmException extends RuntimeException {
    public UnknownAlgorithmException() {
    }

    public UnknownAlgorithmException(String message) {
        super(message);
    }

    public UnknownAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAlgorithmException(Throwable cause) {
        super(cause);
    }
}
