package io.tempo.teams.roles.repository;

import io.tempo.teams.roles.domain.Membership;
import io.tempo.teams.roles.domain.id.MembershipId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MembershipRepository extends CrudRepository<Membership, MembershipId> {

    Optional<Membership> findOneByTeamIdAndUserId(UUID teamId, UUID userId);

    Page<Membership> findAllByRoleId(UUID roleId, Pageable pageable);
}
