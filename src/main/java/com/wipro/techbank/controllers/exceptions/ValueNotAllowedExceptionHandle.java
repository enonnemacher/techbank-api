package com.wipro.techbank.controllers.exceptions;

import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.services.exceptions.ValueNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


public class ValueNotAllowedExceptionHandle {

    @ExceptionHandler(ValueNotAllowedException.class)
    public ResponseEntity<StandardError> valueNotAllowed(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Account balance is insufficient");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
