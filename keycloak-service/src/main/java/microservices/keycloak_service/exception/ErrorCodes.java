package microservices.keycloak_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum ErrorCodes {
    USER_NOT_FOUND(1000, "User not found", HttpStatus.NOT_FOUND),
    INVALID_USER(1001, "Invalid user", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY(1002, "Invalid category", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1003, "Unauthorized", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1004, "Invalid token", HttpStatus.UNAUTHORIZED),
    OTHER_ERROR(1005, "Other error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_LANGUAGE(1006, "Invalid language", HttpStatus.BAD_REQUEST),
    LANGUAGE_EXISTED(1007, "Language already existed", HttpStatus.CONFLICT),
    INVALID_MESSAGE_TRANSLATION(1008, "Invalid message translation", HttpStatus.BAD_REQUEST),
    MESSAGE_TRANSLATION_EXISTED(1009, "Message translation already existed", HttpStatus.CONFLICT),
    INVALID_ATTRIBUTE_TYPE(1010, "Invalid attribute type", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE(1011, "Invalid attribute", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_TRANSLATION(1012, "Invalid category translation", HttpStatus.BAD_REQUEST),
    UPDATE_FAILED(1013, "Update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_STORE(1014, "Invalid store", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1015, "Category already existed", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(1016, "Category not found", HttpStatus.NOT_FOUND),
    OLD_PASSWORD_NOT_MATCH(1017, "Old password not match", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1018, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1019, "Birthday must be over ${min}", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1020, "Password must be at least ${min} characters including at least 1 uppercase, 1 special letter and 1 number", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1021, "Email or Username existed", HttpStatus.CONFLICT),
    INVALID_TEMPLATE(1022, "Invalid template", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1023, "Not found", HttpStatus.NOT_FOUND),
    CONFLICT(1024, "Data existed", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(1025, "${roleName} Role Not found", HttpStatus.NOT_FOUND),
    ;
    Integer code;
    String message;
    HttpStatusCode httpStatusCode;
}