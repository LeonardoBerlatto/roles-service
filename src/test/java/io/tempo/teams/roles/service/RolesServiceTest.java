package io.tempo.teams.roles.service;

import io.tempo.teams.roles.domain.Role;
import io.tempo.teams.roles.exception.BadRequestException;
import io.tempo.teams.roles.repository.RolesRepository;
import io.tempo.teams.roles.web.representation.request.RoleRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {

    @Mock
    private RolesRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RolesService rolesService;

    @Test
    @DisplayName("Should create a new role")
    void testCreateRole() {
        // arrange
        final var name = RandomStringUtils.randomAlphabetic(10);
        final var request = RoleRequest.builder()
                .name(name)
                .build();

        final var role = new Role();
        role.setName(name);

        when(repository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(mapper.map(any(RoleRequest.class), any()))
                .thenReturn(role);

        when(repository.save(any(Role.class)))
                .thenReturn(role);

        // act
        final var result = rolesService.createRole(request);

        // assert
        verify(repository).findByName(name);
        verify(mapper).map(request, Role.class);
        verify(repository).save(role);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should not create a new role when it already exists")
    void testCreateRole_whenItAlreadyExists() {
        // arrange
        final var name = RandomStringUtils.randomAlphabetic(10);
        final var request = RoleRequest.builder()
                .name(name)
                .build();

        final var role = new Role();
        role.setName(name);

        when(repository.findByName(anyString()))
                .thenReturn(Optional.of(role));

        // act
        assertThrows(BadRequestException.class, () -> rolesService.createRole(request));

        // assert
        verify(repository).findByName(name);
        verifyNoInteractions(mapper);
        verify(repository, times(0)).save(role);
    }

    @Test
    @DisplayName("Should check if role exists")
    void testExistsById() {
        // arrange
        final var exists = RandomUtils.nextBoolean();
        when(repository.existsById(any(UUID.class)))
                .thenReturn(exists);

        final var roleId = UUID.randomUUID();

        // act
        final var result = rolesService.existsById(roleId);

        // assert
        verify(repository).existsById(roleId);
        assertEquals(exists, result);
    }

}