package matt.pas.myflp.domain.user.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UserEmailValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface UserEmailConstraint {
    String message() default "Podany adres email jest już zajęty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
