package ru.romanov.watchtogether.exception;

public class UsernameUniqueException extends RuntimeException {

    public UsernameUniqueException(String message) {
        super(message);
    }
}
