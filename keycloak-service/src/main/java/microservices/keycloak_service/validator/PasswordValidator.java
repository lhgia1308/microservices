package microservices.keycloak_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import microservices.keycloak_service.utils.Utils;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private int min;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(Utils.isValidPassword(password, min)) {
            return true;
        }

        return false;
    }
}
