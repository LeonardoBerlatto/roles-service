package io.tempo.teams.roles.web;

import io.tempo.teams.roles.web.representation.request.MembershipRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MembershipControllerTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateMembership_whenRoleDoesNotExist() throws Exception {
        final var roleId = UUID.randomUUID();
        final var teamId = UUID.randomUUID();
        final var userId = UUID.randomUUID();
        final var body = MembershipRequest.builder()
                .roleId(roleId)
                .teamId(teamId)
                .userId(userId)
                .build();

        mockMvc.perform(post("/memberships")
                        .content(asJson(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Role not found"));
    }


    @Test
    void testGetMembershipsByRoleId() throws Exception {
        final var roleId = UUID.randomUUID();
        final var teamId = UUID.randomUUID();
        final var userId = UUID.randomUUID();
        final var body = MembershipRequest.builder()
                .roleId(roleId)
                .teamId(teamId)
                .userId(userId)
                .build();

        mockMvc.perform(get("/memberships/role/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));

    }
}