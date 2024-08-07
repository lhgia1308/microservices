package microservices.keycloak_service.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import microservices.keycloak_service.dto.EntityResponse;
import microservices.keycloak_service.dto.PageResponse;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.dto.request.RoleRequest;
import microservices.keycloak_service.dto.request.UserRequest;
import microservices.keycloak_service.dto.request.UserUpdateRequest;
import microservices.keycloak_service.dto.response.UserResponse;
import microservices.keycloak_service.exception.AppException;
import microservices.keycloak_service.exception.ErrorCodes;
import microservices.keycloak_service.security.KeycloakSecurityUtil;
import microservices.keycloak_service.utils.Utils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

    public PageResponse getUsers(Map<String, Object> params) {
        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        UsersResource usersResource = keycloak.realm(realm).users();
        List<UserRepresentation> userReps = usersResource.list();

        List<UserResponse> userResList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userReps)) {
            userReps.forEach(
                    userRep -> userResList.add(
                        UserResponse.mapUserResponse(
                                Map.ofEntries(
                                        Map.entry("userRepresentation", userRep),
                                        Map.entry("userResource", usersResource.get(userRep.getId()))
                                )
                        )
                    )
            );
        }

        Page<String> page = Utils.paginate(
                Map.ofEntries(
                        Map.entry("pageNo", pageNo),
                        Map.entry("pageSize", pageSize),
                        Map.entry("data", userResList)
                )
        );

        return PageResponse.mapPageResponse(page);
    }

    public UserResponse getUser(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        try {
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            UserRepresentation userRep = userResource.toRepresentation();

            return UserResponse.mapUserResponse(
                    Map.ofEntries(
                            Map.entry("userRepresentation", userRep),
                            Map.entry("userResource", userResource)
                    )
            );
        }
         catch (Exception e) {
            throw new AppException(ErrorCodes.OTHER_ERROR, e.getMessage());
         }
    }

    public UserResponse createUser(Map<String, Object> params) {
        UserRequest userRequest = (UserRequest) params.get("userRequest");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        UserRepresentation userRep = UserRequest.mapUserRepresentation(userRequest);

        Response response = keycloak.realm(realm).users().create(userRep);

        if(response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            UserRepresentation createdUser = userResource.toRepresentation();

            return UserResponse.mapUserResponse(
                    Map.ofEntries(
                            Map.entry("userRepresentation", createdUser),
                            Map.entry("userResource", userResource)
                    )
            );
        }
        else {
            throw new AppException(ErrorCodes.OTHER_ERROR, response.readEntity(EntityResponse.class).getErrorMessage());
        }
    }

    public UserResponse updateUser(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        UserUpdateRequest userRequest = (UserUpdateRequest) params.get("userRequest");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        UserResource userResource = keycloak.realm(realm).users().get(userId);

        UserRepresentation userUpdateRep = UserUpdateRequest.mapUserRepresentation(userId, userRequest);

        userResource.update(userUpdateRep);

        UserRepresentation updatedUser = keycloak.realm(realm).users().get(userId).toRepresentation();

        return UserResponse.mapUserResponse(
                Map.ofEntries(
                        Map.entry("userRepresentation", updatedUser),
                        Map.entry("userResource", userResource)
                )
        );
    }

    public String deleteUsers(Map<String, Object> params) {
        List<String> userIds = (List<String>) params.get("userIds");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        userIds.forEach(userId -> {
            keycloak.realm(realm).users().delete(userId);
        });

        return CollectionUtil.join(userIds, ",");
    }

    public List<Role> getUserRoles(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        List<RoleRepresentation> roleReps = keycloak.realm(realm).users().get(userId).roles().realmLevel().listAll();

        List<Role> roles = new ArrayList<>();
        roleReps.forEach(roleRep -> roles.add(Role.mapRole(roleRep)));

        return roles;
    }

    public UserResponse createUserRole(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        List<String> roleRequestList = (List<String>) params.get("roleRequestList");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();

        UserResource userResource = keycloak.realm(realm).users().get(userId);
        try {
            userResource.toRepresentation();
        }
        catch (Exception e) {
            throw new AppException(ErrorCodes.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND.getMessage());
        }

        List<RoleRepresentation> roleReps = new ArrayList<>();
        roleRequestList.forEach(
                roleReq -> {
                    try {
                        RoleRepresentation roleRep = keycloak.realm(realm).roles().get(roleReq).toRepresentation();
                        roleReps.add(roleRep);
                    }
                    catch (Exception e) {
                        throw new AppException(ErrorCodes.ROLE_NOT_FOUND, Utils.stringSubstitution(ErrorCodes.ROLE_NOT_FOUND.getMessage(), Map.of("roleName", roleReq)));
                    }
                }
        );

        userResource.roles().realmLevel().add(roleReps);

        return UserResponse.mapUserResponse(
                Map.ofEntries(
                        Map.entry("userRepresentation", userResource.toRepresentation()),
                        Map.entry("userResource", userResource)
                )
        );
    }

    public UserResponse revokeUserRole(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        List<String> roleRequestList = (List<String>) params.get("roleRequestList");
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        UserResource userResource = keycloak.realm(realm).users().get(userId);

        try {
            userResource.toRepresentation();
        }
        catch (Exception e) {
            throw new AppException(ErrorCodes.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND.getMessage());
        }

        List<RoleRepresentation> roleReps = new ArrayList<>();
        roleRequestList.forEach(
                roleReq -> {
                    try {
                        RoleRepresentation roleRep = keycloak.realm(realm).roles().get(roleReq).toRepresentation();
                        roleReps.add(roleRep);
                    }
                    catch (Exception e) {
                        throw new AppException(ErrorCodes.ROLE_NOT_FOUND, Utils.stringSubstitution(ErrorCodes.ROLE_NOT_FOUND.getMessage(), Map.of("roleName", roleReq)));
                    }
                }
        );

        userResource.roles().realmLevel().remove(roleReps);

        return UserResponse.mapUserResponse(
                Map.ofEntries(
                        Map.entry("userRepresentation", userResource.toRepresentation()),
                        Map.entry("userResource", userResource)
                )
        );
    }
}
