package com.wso2.openbanking.fdx.identity.dcr.validation.annotation;

import com.wso2.openbanking.fdx.identity.dcr.validation.impl.FDXParametersMatchValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation class to validate whether the registration request values match those in the SSA.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FDXParametersMatchValidator.class})
public @interface ValidateFDXParametersMatch {
    String message() default "Provided request parameters do not match with the SSA";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
