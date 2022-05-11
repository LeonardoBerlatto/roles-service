package io.tempo.teams.roles.service;

import io.tempo.teams.roles.domain.Role;
import io.tempo.teams.roles.exception.BadRequestException;
import io.tempo.teams.roles.repository.RolesRepository;
import io.tempo.teams.roles.web.representation.request.RoleRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolesService {

    private final RolesRepository repository;

    private final ModelMapper mapper;

    public Role createRole(final RoleRequest request) {
        checkRoleName(request);
        return repository.save(mapper.map(request, Role.class));
    }

    public boolean existsById(final UUID roleId) {
        return repository.existsById(roleId);
    }

    private void checkRoleName(final RoleRequest request) {
        final Optional<Role> role = repository.findByName(request.getName());
        if (role.isPresent()) {
            throw new BadRequestException("Role with name " + request.getName() + " already exists");
        }
    }
}
