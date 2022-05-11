package io.tempo.teams.roles.web.representation.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipRequest {

    @NotNull(message = "Team id is required")
    private UUID teamId;

    @NotNull(message = "Role id is required")
    private UUID roleId;

    @NotNull(message = "User id is required")
    private UUID userId;
}
