package com.wso2.openbanking.fdx.identity.dcr.validation.annotation;

import com.wso2.openbanking.fdx.identity.dcr.validation.impl.DurationTypeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation class for duration type validation.
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DurationTypeValidator.class})
public @interface ValidateDurationType {
    String message() default "Requested duration types are not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
