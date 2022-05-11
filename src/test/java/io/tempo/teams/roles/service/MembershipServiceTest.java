package io.tempo.teams.roles.service;

import io.tempo.teams.roles.domain.Membership;
import io.tempo.teams.roles.domain.id.MembershipId;
import io.tempo.teams.roles.exception.BadRequestException;
import io.tempo.teams.roles.exception.NotFoundException;
import io.tempo.teams.roles.repository.MembershipRepository;
import io.tempo.teams.roles.web.representation.request.MembershipRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RolesService rolesService;

    @Mock
    private MembershipRepository repository;

    @InjectMocks
    private MembershipService membershipService;

    @Test
    @DisplayName("Should create a membership")
    void testCreateMembership() {
        // arrange
        final var membership = new Membership();
        when(modelMapper.map(any(), any()))
                .thenReturn(membership);

        when(rolesService.existsById(any(UUID.class)))
                .thenReturn(true);

        when(repository.existsById(any(MembershipId.class)))
                .thenReturn(false);

        when(repository.save(any(Membership.class)))
                .thenReturn(membership);

        final var membershipRequest = new MembershipRequest();
        membershipRequest.setRoleId(UUID.randomUUID());
        membershipRequest.setTeamId(UUID.randomUUID());
        membershipRequest.setUserId(UUID.randomUUID());

        // act
        final var result = membershipService.createMembership(membershipRequest);

        // assert
        verify(modelMapper).map(membershipRequest, Membership.class);
        verify(rolesService).existsById(membershipRequest.getRoleId());
        verify(repository).existsById(any());
        verify(repository).save(membership);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw NotFoundException when role does not exist")
    void testCreateMembership_whenRoleDoesNotExist() {
        // arrange
        final var membership = new Membership();
        when(modelMapper.map(any(), any()))
                .thenReturn(membership);

        when(rolesService.existsById(any(UUID.class)))
                .thenReturn(false);

        final var membershipRequest = new MembershipRequest();
        membershipRequest.setRoleId(UUID.randomUUID());
        membershipRequest.setTeamId(UUID.randomUUID());
        membershipRequest.setUserId(UUID.randomUUID());

        // act
        assertThrows(NotFoundException.class, () -> membershipService.createMembership(membershipRequest));

        // assert
        verify(modelMapper).map(membershipRequest, Membership.class);
        verify(rolesService).existsById(membershipRequest.getRoleId());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should throw BadRequestException when membership already exists")
    void testCreateMembership_whenMembershipAlreadyExists() {
        // arrange
        final var membership = new Membership();
        when(modelMapper.map(any(), any()))
                .thenReturn(membership);

        when(rolesService.existsById(any(UUID.class)))
                .thenReturn(true);

        when(repository.existsById(any(MembershipId.class)))
                .thenReturn(true);

        final var membershipRequest = new MembershipRequest();
        membershipRequest.setRoleId(UUID.randomUUID());
        membershipRequest.setTeamId(UUID.randomUUID());
        membershipRequest.setUserId(UUID.randomUUID());

        // act
        assertThrows(BadRequestException.class, () -> membershipService.createMembership(membershipRequest));

        // assert
        verify(modelMapper).map(membershipRequest, Membership.class);
        verify(rolesService).existsById(membershipRequest.getRoleId());
        verify(repository).existsById(any());
        verify(repository, times(0)).save(membership);
    }

    @Test
    @DisplayName("Should return membership when membership exists")
    void testGetMembershipByTeamAndUser() {
        // arrange
        when(repository.findOneByTeamIdAndUserId(any(UUID.class), any(UUID.class)))
                .thenReturn(Optional.of(new Membership()));

        final var teamId = UUID.randomUUID();
        final var userId = UUID.randomUUID();

        // act
        final var result = membershipService.getMembershipByTeamAndUser(teamId, userId);

        // assert
        verify(repository).findOneByTeamIdAndUserId(teamId, userId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw NotFoundException when membership does not exist")
    void testGetMembershipByTeamAndUser_whenMembershipDoesNotExist() {
        // arrange
        when(repository.findOneByTeamIdAndUserId(any(UUID.class), any(UUID.class)))
                .thenReturn(Optional.empty());

        final var teamId = UUID.randomUUID();
        final var userId = UUID.randomUUID();

        // act
        assertThrows(NotFoundException.class, () -> membershipService.getMembershipByTeamAndUser(teamId, userId));

        // assert
        verify(repository).findOneByTeamIdAndUserId(teamId, userId);
    }

    @Test
    @DisplayName("Should return a page of existing memberships")
    void testGetMembershipsByRoleId() {
        // arrange
        when(repository.findAllByRoleId(any(UUID.class), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(new Membership())));

        final var roleId = UUID.randomUUID();

        final var pageable = PageRequest.of(0, 10);

        // act
        final var result = membershipService.getMembershipsByRoleId(roleId, pageable);

        // assert
        verify(repository).findAllByRoleId(roleId, pageable);
        assertNotNull(result);
    }
}