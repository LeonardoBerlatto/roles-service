package io.tempo.teams.roles.service;

import io.tempo.teams.roles.domain.Membership;
import io.tempo.teams.roles.domain.id.MembershipId;
import io.tempo.teams.roles.exception.BadRequestException;
import io.tempo.teams.roles.exception.NotFoundException;
import io.tempo.teams.roles.repository.MembershipRepository;
import io.tempo.teams.roles.web.representation.request.MembershipRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MembershipService {

    private final MembershipRepository repository;

    private final RolesService rolesService;

    private final ModelMapper modelMapper;

    public Membership createMembership(final MembershipRequest membershipRequest) {
        final var membership = modelMapper.map(membershipRequest, Membership.class);
        if (!rolesService.existsById(membershipRequest.getRoleId())) {
            throw new NotFoundException("Role not found");
        }

        final var id = new MembershipId(
                membershipRequest.getTeamId(),
                membershipRequest.getUserId()
        );

        if (repository.existsById(id)) {
            throw new BadRequestException("Membership already exists");
        }

            return repository.save(membership);
    }

    public Membership getMembershipByTeamAndUser(final UUID teamId, final UUID userId) {
        return repository.findOneByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));
    }

    public Page<Membership> getMembershipsByRoleId(final UUID roleId, final Pageable pageable) {
        return repository.findAllByRoleId(roleId, pageable);
    }
}
