package microservices.keycloak_service.service;

import lombok.RequiredArgsConstructor;
import microservices.keycloak_service.dto.request.RoleRequest;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.exception.AppException;
import microservices.keycloak_service.exception.ErrorCodes;
import microservices.keycloak_service.security.KeycloakSecurityUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final KeycloakSecurityUtil keycloakSecurityUtil;

    @Value("${keycloak.realm}")
    private String realm;

    public List<Role> getRoles(Map<String, Object> params) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        List<RoleRepresentation> roleRes = keycloak.realm(realm).roles().list();
        List<Role> roles = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(roleRes)) {
            roleRes.forEach(roleRe -> roles.add(Role.mapRole(roleRe)));
        }

        return roles;
    }

    public Role getRole(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        var roleRe = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        return Role.mapRole(roleRe);
    }

    public Role createRole(Map<String, Object> params) throws Exception {
        RoleRequest roleRequest = (RoleRequest) params.get("roleRequest");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        RoleRepresentation roleRe = RoleRequest.mapRoleRepresentation(roleRequest);
        try {
            keycloak.realm(realm).roles().create(roleRe);
            var newRoleRe = keycloak.realm(realm).roles().get(roleRequest.getName()).toRepresentation();

            return Role.mapRole(newRoleRe);
        }
        catch(Exception e) {
            throw new AppException(ErrorCodes.OTHER_ERROR, e.getMessage());
        }
    }

    public Role updateRole(Map<String, Object> params) throws Exception {
        RoleRequest roleRequest = (RoleRequest) params.get("roleRequest");
        String roleName = (String) params.get("roleName");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        try {
            keycloak.realm(realm).roles().get(roleName)
                    .update(RoleRequest.mapRoleRepresentation(roleRequest));

            var newRoleRe = keycloak.realm(realm).roles().get(roleRequest.getName()).toRepresentation();

            return Role.mapRole(newRoleRe);
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
