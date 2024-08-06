package microservices.keycloak_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.UserRepresentation;

@Getter
@Setter
@Builder
public class UserResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    public static UserResponse mapUserResponse(UserRepresentation userRepresentation) {
       return UserResponse.builder()
               .id(userRepresentation.getId())
               .firstName(userRepresentation.getFirstName())
               .lastName(userRepresentation.getLastName())
               .email(userRepresentation.getEmail())
               .userName(userRepresentation.getUsername())
               .build();
    }
}