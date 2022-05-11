package io.tempo.teams.roles.web;


import io.tempo.teams.roles.service.MembershipService;
import io.tempo.teams.roles.service.RolesService;
import io.tempo.teams.roles.web.representation.request.RoleRequest;
import io.tempo.teams.roles.web.representation.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolesController {

    private final RolesService service;

    private final MembershipService membershipService;

    private final ModelMapper mapper;


    @GetMapping("/team/{teamId}/user/{userId}")
    public RoleResponse getRoleByMembership(@NotNull @PathVariable("teamId") final UUID teamId,
                                             @NotNull @PathVariable("userId") final UUID userId) {
        final var role = membershipService.getMembershipByTeamAndUser(teamId, userId).getRole();
        return mapper.map(role, RoleResponse.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RoleResponse createRole(@Valid @RequestBody final RoleRequest request) {
        final var role = service.createRole(request);
        return mapper.map(role, RoleResponse.class);
    }

}
