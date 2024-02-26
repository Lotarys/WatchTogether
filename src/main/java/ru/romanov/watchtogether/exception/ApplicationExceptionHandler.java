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
            UsernameUniqueException.class,
            UserOperationException.class,
            PlaylistException.class,
            CreateRoomException.class
    })
    public ResponseEntity<?> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof UsernameUniqueException) {
            status = HttpStatus.CONFLICT;
        } else if(e instanceof RoomNotFoundException ||
                e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(e.getMessage());
    }
}
