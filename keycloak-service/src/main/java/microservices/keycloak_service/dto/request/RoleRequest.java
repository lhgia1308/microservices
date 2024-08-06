package microservices.keycloak_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.RoleRepresentation;

@Getter
@Setter
public class RoleRequest {
    private String name;

    public static RoleRepresentation mapRoleRepresentation(RoleRequest roleRequest) {
        var roleRe = new RoleRepresentation();
        roleRe.setName(roleRequest.getName());
        return roleRe;
    }
}
