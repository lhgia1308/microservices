package microservices.keycloak_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.RoleRepresentation;

@Getter
@Setter
@Builder
public class Role {
    private String id;

    private String name;

    public static Role mapRole(RoleRepresentation roleRepresentation) {
        return Role.builder()
                .id(roleRepresentation.getId())
                .name(roleRepresentation.getName())
                .build();
    }

    public static RoleRepresentation mapRoleRepresentation(Role role) {
        var roleRe = new RoleRepresentation();
        roleRe.setId(role.getId());
        roleRe.setName(role.getName());
        return roleRe;
    }
}