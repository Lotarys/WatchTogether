package ru.romanov.watchtogether.exception;

public class CreateRoomException extends RuntimeException {
    public CreateRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
