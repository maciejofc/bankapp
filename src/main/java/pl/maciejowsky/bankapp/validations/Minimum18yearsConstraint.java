package pl.maciejowsky.bankapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Minimum18yearsConstraint {
    String message() default "You need to have minimum 18y old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
