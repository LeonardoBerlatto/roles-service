package io.tempo.teams.roles.web.error;

import io.tempo.teams.roles.exception.BadRequestException;
import io.tempo.teams.roles.exception.NotFoundException;
import io.tempo.teams.roles.exception.RestException;
import io.tempo.teams.roles.web.representation.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class, NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(RestException ex) {
        return buildResponseEntity(ex);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(final RestException exception) {
        final var response = ErrorResponse.builder()
                .code(exception.getHttpStatus().value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

}