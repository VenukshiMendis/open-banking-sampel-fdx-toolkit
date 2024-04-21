package com.wso2.openbanking.fdx.identity.dcr.validation.impl;

import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateRedirectURIsMatch;

import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator class to validate if the redirect URIs provided in the registration request
 * match with those in the SSA.
 */
public class RedirectURIsMatchValidator implements ConstraintValidator<ValidateRedirectURIsMatch , Object> {

    @Override
    public boolean isValid(Object fdxRegistrationRequestObj, ConstraintValidatorContext constraintValidatorContext) {
        FDXRegistrationRequest fdxRegistrationRequest = (FDXRegistrationRequest) fdxRegistrationRequestObj;

        //check whether redirect uris in request match with the redirect uris in SSA
        Set<String> redirectURIsFromRequest = new HashSet<>(fdxRegistrationRequest.getCallbackUris());
        Set<String> redirectURIsFromSSA =
                new HashSet<>(fdxRegistrationRequest.getSoftwareStatementBody().getCallbackUris());

        if (!redirectURIsFromRequest.equals(redirectURIsFromSSA)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("Provided redirect URIs do not match with the SSA:"
                            + DCRCommonConstants.INVALID_META_DATA).addConstraintViolation();
            return false;
        }
        return true;
    }

}


