package microservices.keycloak_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import microservices.keycloak_service.dto.ApiResponse;
import microservices.keycloak_service.dto.User;
import microservices.keycloak_service.dto.response.UserResponse;
import microservices.keycloak_service.service.UserService;
import microservices.keycloak_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/keycloak/user")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public ApiResponse<List<UserResponse>> getUsers() {
        return new ApiResponse<>(Constants.SUCCESS, HttpStatus.OK.value(), userService.getUsers(null));
    }
}
