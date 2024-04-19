package com.wso2.openbanking.fdx.identity.dcr.validation.annotation;

import com.wso2.openbanking.fdx.identity.dcr.validation.impl.ScopesValidator;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation class for scope validation.
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ScopesValidator.class})
public @interface ValidateScopes {

    String message() default "Requested scope is not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
