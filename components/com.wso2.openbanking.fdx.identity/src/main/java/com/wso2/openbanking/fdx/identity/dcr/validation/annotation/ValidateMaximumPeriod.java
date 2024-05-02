package com.wso2.openbanking.fdx.identity.dcr.validation.annotation;

import com.wso2.openbanking.fdx.identity.dcr.validation.impl.MaximumPeriodValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation class for maximum Duration Period and maximum Lookback Period validation.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MaximumPeriodValidator.class})
public @interface ValidateMaximumPeriod {
    String message() default "Provided duration period or lookback period exceeds the maximum allowed period";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
