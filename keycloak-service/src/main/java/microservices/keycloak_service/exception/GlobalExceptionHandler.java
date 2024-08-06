package microservices.keycloak_service.exception;

import jakarta.ws.rs.NotFoundException;
import microservices.keycloak_service.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
