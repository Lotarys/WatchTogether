package ru.romanov.watchtogether.exception;

public class UserOperationException extends RuntimeException {

    public UserOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
