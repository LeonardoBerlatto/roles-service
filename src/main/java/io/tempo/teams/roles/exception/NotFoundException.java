package io.tempo.teams.roles.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException implements RestException {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
