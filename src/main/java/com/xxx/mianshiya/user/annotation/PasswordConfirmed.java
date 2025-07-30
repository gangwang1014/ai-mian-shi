package com.xxx.mianshiya.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConfirmedValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmed {

    String message() default "Password and confirmation do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordField();
    String confirmField();
}
