package microservices.keycloak_service.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private ErrorCodes errorCode;
    private String message;

    public AppException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public AppException(ErrorCodes errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
