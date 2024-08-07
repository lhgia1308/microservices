package microservices.keycloak_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import microservices.keycloak_service.dto.ApiResponse;
import microservices.keycloak_service.dto.PageResponse;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.dto.request.RoleRequest;
import microservices.keycloak_service.dto.request.UserRequest;
import microservices.keycloak_service.dto.request.UserUpdateRequest;
import microservices.keycloak_service.dto.response.UserResponse;
import microservices.keycloak_service.service.UserService;
import microservices.keycloak_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/keycloak/user")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public ApiResponse<PageResponse> getUsers(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        Map<String, Object> args = new HashMap<>();
        args.put("pageNo", pageNo);
        args.put("pageSize", pageSize);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUsers(args));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUser(args));
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        Map<String, Object> args = new HashMap<>();
        args.put("userRequest", userRequest);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.createUser(args));
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest userRequest) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("userRequest", userRequest);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.updateUser(args));
    }

    @DeleteMapping
    public ApiResponse deleteUsers(@RequestBody List<String> userIds) {
        Map<String, Object> args = new HashMap<>();
        args.put("userIds", userIds);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.deleteUsers(args));
    }

    @GetMapping("/{userId}/roles")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable String userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUserRoles(args));
    }

    @PostMapping("/{userId}/roles")
    public ApiResponse<UserResponse> createUserRole(@PathVariable String userId, @RequestBody List<String> roleRequestList) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("roleRequestList", roleRequestList);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.createUserRole(args));
    }

    @PostMapping("/{userId}/roles/revoke")
    public ApiResponse<UserResponse> revokeUserRole(@PathVariable String userId, @RequestBody List<String> roleRequestList) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("roleRequestList", roleRequestList);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.revokeUserRole(args));
    }
}
