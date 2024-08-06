package microservices.keycloak_service.service;

import lombok.RequiredArgsConstructor;
import microservices.keycloak_service.dto.User;
import microservices.keycloak_service.dto.response.UserResponse;
import microservices.keycloak_service.security.KeycloakSecurityUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakSecurityUtil keycloakSecurityUtil;

    @Value("${keycloak.realm}")
    private String realm;

    public List<UserResponse> getUsers(Map<String, String> params) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        List<UserRepresentation> userReps = keycloak.realm(realm).users().list();

        List<UserResponse> userResList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userReps)) {
            userReps.forEach(userRep -> userResList.add(UserResponse.mapUserResponse(userRep)));
        }

        return userResList;
    }
}
