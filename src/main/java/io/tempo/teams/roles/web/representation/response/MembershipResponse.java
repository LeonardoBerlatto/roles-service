package io.tempo.teams.roles.web.representation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponse {
    private UUID roleId;
    private UUID teamId;
    private UUID userId;
}
