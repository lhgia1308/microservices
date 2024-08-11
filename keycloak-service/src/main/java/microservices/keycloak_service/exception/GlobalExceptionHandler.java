package microservices.keycloak_service.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.NotFoundException;
import microservices.keycloak_service.dto.ApiResponse;
import microservices.keycloak_service.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handle(Exception e) {
        var apiResponse = ApiResponse.builder()
                .statusCode(ErrorCodes.OTHER_ERROR.getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(ErrorCodes.OTHER_ERROR.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = NotFoundException.class)
    ResponseEntity<ApiResponse> handle(NotFoundException e) {
        var apiResponse = ApiResponse.builder()
                .statusCode(ErrorCodes.NOT_FOUND.getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(ErrorCodes.NOT_FOUND.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handle(AppException e) {
        var apiResponse = ApiResponse.builder()
                .statusCode(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handle(MethodArgumentNotValidException e) {
        var fieldErrors = e.getBindingResult().getFieldErrors();
        Set<String> errors = new HashSet<>();

        fieldErrors.forEach(field -> {
            ErrorCodes errorCode = null;
            try {
                errorCode = ErrorCodes.valueOf(field.getDefaultMessage());
            }
            catch (Exception ex) {}

            if (errorCode != null) {
                var constraintViolations = field.unwrap(ConstraintViolation.class);
                var attributes = constraintViolations.getConstraintDescriptor().getAttributes();
                var newMessage = Utils.stringSubstitution(errorCode.getMessage(), attributes);
                errors.add(newMessage);
            }
        });

        var apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errors.stream().collect(Collectors.joining(".\n ")))
                .build();

        return ResponseEntity.status(e.getStatusCode().value()).body(apiResponse);
    }
}
