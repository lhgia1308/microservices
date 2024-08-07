package microservices.keycloak_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String password;

    public static UserRepresentation mapUserRepresentation(UserRequest userRequest) {
        UserRepresentation userRep = new UserRepresentation();
//        userRep.setId(String.valueOf(UUID.randomUUID()));
        userRep.setFirstName(userRequest.getFirstName());
        userRep.setLastName(userRequest.getLastName());
        userRep.setEmail(userRequest.getEmail());
        userRep.setUsername(userRequest.getUserName());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(userRequest.getPassword());

        userRep.setCredentials(List.of(cred));

        return userRep;
    }
}