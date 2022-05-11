package io.tempo.teams.roles.domain;

import io.tempo.teams.roles.domain.id.MembershipId;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memberships")
@IdClass(MembershipId.class)
public class Membership {

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID teamId;

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
}
