package microservices.keycloak_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String password;
}