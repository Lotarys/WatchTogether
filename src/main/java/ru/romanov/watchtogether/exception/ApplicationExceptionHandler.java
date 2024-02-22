package ru.romanov.watchtogether.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({
            RoomNotFoundException.class,
            UserNotFoundException.class,
            UsernameUniqueException.class
    })
    public ResponseEntity<?> handleException(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(e instanceof UsernameUniqueException) {
            status = HttpStatus.CONFLICT;
        }
        return ResponseEntity
                .status(status)
                .body(e.getMessage());
    }
}
