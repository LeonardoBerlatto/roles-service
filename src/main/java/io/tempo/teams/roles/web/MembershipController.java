package io.tempo.teams.roles.web;

import io.tempo.teams.roles.domain.Membership;
import io.tempo.teams.roles.service.MembershipService;
import io.tempo.teams.roles.web.representation.request.MembershipRequest;
import io.tempo.teams.roles.web.representation.response.MembershipResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MembershipController {

    private final MembershipService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipResponse createMembership(@RequestBody MembershipRequest membershipRequest) {
        final var membership = service.createMembership(membershipRequest);
        return modelMapper.map(membership, MembershipResponse.class);
    }

    @GetMapping("/role/{roleId}")
    public Page<MembershipResponse> getMembershipsByRoleId(@PathVariable UUID roleId, @PageableDefault(size = 5) Pageable pageable) {
        final var memberships = service.getMembershipsByRoleId(roleId, pageable);
        return mapMemberships(memberships);
    }

    private Page<MembershipResponse> mapMemberships(Page<Membership> memberships) {
        return memberships.map(membership -> modelMapper.map(membership, MembershipResponse.class));
    }

}
