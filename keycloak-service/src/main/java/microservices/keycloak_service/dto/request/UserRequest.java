package microservices.keycloak_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import microservices.keycloak_service.validator.PasswordConstraint;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import java.util.List;

@Getter
@Setter
public class UserRequest {
    private String firstName;

    private String lastName;

    @Email(message = "INVALID_EMAIL")
    private String email;

    private String userName;

    @PasswordConstraint(min = 8)
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