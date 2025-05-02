package kg.attractor.edufood.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.edufood.validation.validator.UniqueEmailValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Пользователь с такой почтой уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
