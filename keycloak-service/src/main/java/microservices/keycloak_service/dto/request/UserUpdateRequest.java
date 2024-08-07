package microservices.keycloak_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import java.util.List;

@Getter
@Setter
public class UserUpdateRequest {
    private String firstName;

    private String lastName;

    public static UserRepresentation mapUserRepresentation(String userId, UserUpdateRequest userRequest) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId(userId);
        userRep.setFirstName(userRequest.getFirstName());
        userRep.setLastName(userRequest.getLastName());

        return userRep;
    }
}
