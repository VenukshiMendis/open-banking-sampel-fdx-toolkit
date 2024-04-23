package com.wso2.openbanking.fdx.identity.dcr.validation.annotation;

import com.wso2.openbanking.fdx.identity.dcr.validation.impl.DurationPeriodValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



/**
 * Annotation  class for validating the duration period when the duration type contains TIME_BOUND.
 */

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DurationPeriodValidator.class})
public @interface ValidateDurationPeriod {
    String message() default "Duration period is required when duration type is TIME_BOUND";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
