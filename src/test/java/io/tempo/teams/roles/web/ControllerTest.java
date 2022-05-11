package io.tempo.teams.roles.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    protected String asJson(final Object body) throws JsonProcessingException {
        return objectMapper.writeValueAsString(body);
    }
}
