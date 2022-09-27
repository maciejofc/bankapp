package pl.maciejowsky.bankapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class YearsValidator implements ConstraintValidator<Minimum18yearsConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        // 18 years is 6570 days
        if (localDate == null)
            return false;
        long amountOfUserDays = DAYS.between(localDate, LocalDate.now());
        return amountOfUserDays >= 6570;
    }

    @Override
    public void initialize(Minimum18yearsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
