package io.tempo.teams.roles.exception;

import org.springframework.http.HttpStatus;

public interface RestException {

    String getMessage();

    HttpStatus getHttpStatus();
}
