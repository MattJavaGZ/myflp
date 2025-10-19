package matt.pas.myflp.domain.user.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import matt.pas.myflp.domain.user.UserService;

public class UserEmailValidator implements ConstraintValidator<UserEmailConstraint, String> {

    private final UserService userService;

    public UserEmailValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void initialize(UserEmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isEmailExist(email);
    }
}
