package io.tempo.teams.roles.repository;

import io.tempo.teams.roles.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends CrudRepository<Role, UUID> {

    Optional<Role> findByName(String name);
}
