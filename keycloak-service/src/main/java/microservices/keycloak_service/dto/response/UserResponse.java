package microservices.keycloak_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.security.KeycloakSecurityUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private List<RoleRepresentation> roles = new ArrayList<>();

    public static UserResponse mapUserResponse(Map<String, Object> params) {
        UserRepresentation userRepresentation = (UserRepresentation) params.get("userRepresentation");
        UserResource userResource = (UserResource) params.get("userResource");
        List<RoleRepresentation> roles = userResource == null ? null : userResource.roles().realmLevel().listAll();

        return UserResponse.builder()
               .id(userRepresentation.getId())
               .firstName(userRepresentation.getFirstName())
               .lastName(userRepresentation.getLastName())
               .email(userRepresentation.getEmail())
               .userName(userRepresentation.getUsername())
               .roles(roles)
               .build();
    }
}