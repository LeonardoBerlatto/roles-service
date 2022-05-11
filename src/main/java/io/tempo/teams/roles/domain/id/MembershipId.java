package io.tempo.teams.roles.domain.id;

import lombok.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MembershipId implements Serializable {

    @Type(type = "uuid-char")
    private UUID teamId;
    @Type(type = "uuid-char")
    private UUID userId;
}
