package com.wso2.openbanking.fdx.identity.dcr.validation.impl;

import com.google.gson.annotations.SerializedName;
import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXSoftwareStatementBody;
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateFDXParametersMatch;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator class to validate if the registration request values match with those in the SSA.
 */
public class FDXParametersMatchValidator implements ConstraintValidator<ValidateFDXParametersMatch, Object> {
    private static final Log log = LogFactory.getLog(FDXParametersMatchValidator.class);


    @Override
    public boolean isValid(Object fdxRegistrationRequestObj, ConstraintValidatorContext constraintValidatorContext) {
        FDXRegistrationRequest fdxRegistrationRequest = (FDXRegistrationRequest) fdxRegistrationRequestObj;

        FDXSoftwareStatementBody fdxSoftwareStatementBody =
                (FDXSoftwareStatementBody) fdxRegistrationRequest.getSoftwareStatementBody();
        try {
            for (Field field : fdxRegistrationRequest.getClass().getDeclaredFields()) {
                if (!field.isSynthetic()) {

                    field.setAccessible(true);
                    // Get the value of the field from the fdxRegistrationRequest object
                    Object requestValue = field.get(fdxRegistrationRequest);

                    // Get the same field from fdxSoftwareStatement using field name
                    Field ssaField = FieldUtils.getField(fdxSoftwareStatementBody.getClass(), field.getName(), true);

                    ssaField.setAccessible(true);
                    Object ssaValue = ssaField.get(fdxSoftwareStatementBody);

                    // compare SSA field value with the value in registration request
                    if (requestValue != null && ssaValue != null && !requestValue.equals(ssaValue)) {
                        SerializedName annotation = field.getAnnotation(SerializedName.class);
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext
                                .buildConstraintViolationWithTemplate("Provided " + annotation.value() +
                                        " value does not match with the SSA:" + DCRCommonConstants.INVALID_META_DATA)
                                .addConstraintViolation();
                        return false;
                    }
                }
            }

        } catch (IllegalAccessException e) {
            log.error("Error while resolving validation fields", e);
            return false;

        }

        return true;
    }


}
