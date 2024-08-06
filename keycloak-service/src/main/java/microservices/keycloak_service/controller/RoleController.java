package microservices.keycloak_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.dto.request.RoleRequest;
import microservices.keycloak_service.service.RoleService;
import microservices.keycloak_service.dto.ApiResponse;
import microservices.keycloak_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/keycloak/role")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService keycloakRoleService;

    @GetMapping("/list")
    public ApiResponse<List<Role>> getRoles() {
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), keycloakRoleService.getRoles(null));
    }

    @GetMapping("/{roleName}")
    public ApiResponse<Role> getRole(@PathVariable String roleName) {
        Map<String, Object> args = new HashMap<>();
        args.put("roleName", roleName);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), keycloakRoleService.getRole(args));
    }

    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody RoleRequest roleRequest) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("roleRequest", roleRequest);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), keycloakRoleService.createRole(args));
    }

    @PutMapping("/{roleName}")
    public ApiResponse<Role> updateRole(@PathVariable String roleName, @RequestBody RoleRequest roleRequest) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("roleRequest", roleRequest);
        args.put("roleName", roleName);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), keycloakRoleService.updateRole(args));
    }
}
