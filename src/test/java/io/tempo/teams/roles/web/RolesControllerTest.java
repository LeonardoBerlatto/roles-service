package io.tempo.teams.roles.web;

import io.tempo.teams.roles.web.representation.request.RoleRequest;
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
class RolesControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateRole() throws Exception {
        final var body = RoleRequest.builder()
                .name("tester-1")
                .build();

        mockMvc.perform(post("/roles")
                        .content(asJson(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("tester-1"));
    }

    @Test
    void testCreateRole_withInvalidBody() throws Exception {
        final var body = RoleRequest.builder()
                .name(null)
                .build();

        mockMvc.perform(post("/roles")
                        .content(asJson(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateRole_withExistingRole() throws Exception {
        final var roleName = "Developer";
        final var body = RoleRequest.builder()
                .name(roleName)
                .build();

        mockMvc.perform(post("/roles")
                        .content(asJson(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Role with name " + roleName + " already exists"));
    }

    @Test
    void testGetRole_whenMembershipDoesNotExist() throws Exception {
        final var teamId = UUID.randomUUID();
        final var userId = UUID.randomUUID();

        mockMvc.perform(get("/roles/team/" + teamId + "/user/" + userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Membership not found"));
    }


}