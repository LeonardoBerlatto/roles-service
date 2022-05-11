package io.tempo.teams.roles.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException implements RestException {
    public BadRequestException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

