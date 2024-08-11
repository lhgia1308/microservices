package microservices.keycloak_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservices.keycloak_service.dto.ApiResponse;
import microservices.keycloak_service.dto.PageResponse;
import microservices.keycloak_service.dto.Role;
import microservices.keycloak_service.dto.request.UserRequest;
import microservices.keycloak_service.dto.request.UserUpdateRequest;
import microservices.keycloak_service.dto.response.UserResponse;
import microservices.keycloak_service.service.UserService;
import microservices.keycloak_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/keycloak/user")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
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
    @PreAuthorize("#userId == authentication.name || hasAuthority('admin')")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUser(args));
    }

    @GetMapping("/username/{userName}")
    public ApiResponse<List<UserResponse>> getUserByUserName(@PathVariable String userName) {
        Map<String, Object> args = new HashMap<>();
        args.put("userName", userName);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUserByUserName(args));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<UserResponse> createUser(
            @RequestBody @Valid UserRequest userRequest,
            Authentication authentication
    ) {
        Map<String, Object> args = new HashMap<>();
        args.put("userRequest", userRequest);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.createUser(args));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.name || hasAuthority('admin')") //before
//    @PostAuthorize("returnObject.data.id == authentication.principal.nickName") //after
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest userRequest,
            Authentication authentication
    ) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("userRequest", userRequest);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.updateUser(args));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ApiResponse deleteUsers(@RequestBody List<String> userIds) {
        Map<String, Object> args = new HashMap<>();
        args.put("userIds", userIds);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.deleteUsers(args));
    }

    @DeleteMapping("/username")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ApiResponse deleteUserByUserName(@RequestBody List<String> userNames) {
        Map<String, Object> args = new HashMap<>();
        args.put("userNames", userNames);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.deleteUserByUserName(args));
    }

    @GetMapping("/{userId}/roles")
    @PreAuthorize("#userId == authentication.name || hasAuthority('admin')")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable String userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUserRoles(args));
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<UserResponse> createUserRole(@PathVariable String userId, @RequestBody List<String> roleRequestList) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("roleRequestList", roleRequestList);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.createUserRole(args));
    }

    @PostMapping("/{userId}/roles/revoke")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<UserResponse> revokeUserRole(@PathVariable String userId, @RequestBody List<String> roleRequestList) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("roleRequestList", roleRequestList);
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.revokeUserRole(args));
    }
}
