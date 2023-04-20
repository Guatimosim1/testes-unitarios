package io.guatimosim1.exception.handler;

import io.guatimosim1.exception.classes.IntegridadeDadosException;
import io.guatimosim1.exception.classes.UserNotFoundException;
import io.guatimosim1.exception.details.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class Handler {
    @Autowired
    private HttpServletRequest servletRequest;
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Details> handleUserNotFoundException(UserNotFoundException exception) {
        Details details = new Details().builder()
                .error("NOT FOUND")
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .path(servletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IntegridadeDadosException.class)
    public ResponseEntity<Details> handleIntegridadeDadosException(IntegridadeDadosException exception) {
        Details details = new Details().builder()
                .error("BAD REQUEST")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .path(servletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
