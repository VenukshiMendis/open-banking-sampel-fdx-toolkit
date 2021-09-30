/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Software License available at https://wso2.com/licenses/eula/3.1.
 * For specific language governing the permissions and limitations under this
 * license, please see the license as well as any agreement you’ve entered into
 * with WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.openbanking.identity.dcr.validation;

import com.wso2.openbanking.accelerator.identity.dcr.exception.DCRValidationException;
import com.wso2.openbanking.accelerator.identity.dcr.model.RegistrationRequest;
import com.wso2.openbanking.accelerator.identity.dcr.validation.DefaultRegistrationValidatorImpl;

import java.util.Map;

/**
 * Sample implementation for dcr registration VALIDATOR class
 */
public class SampleRegistrationValidationImpl extends DefaultRegistrationValidatorImpl {

    @Override
    public void validatePost(RegistrationRequest registrationRequest) throws DCRValidationException {
        super.validatePost(registrationRequest);
    }

    @Override
    public void validateGet(String clientId) throws DCRValidationException {
        super.validateGet(clientId);
    }

    @Override
    public void validateDelete(String clientId) throws DCRValidationException {
        super.validateDelete(clientId);
    }

    @Override
    public void validateUpdate(RegistrationRequest registrationRequest) throws DCRValidationException {
        super.validateUpdate(registrationRequest);
    }

    @Override
    public void setSoftwareStatementPayload(RegistrationRequest registrationRequest, String decodedSSA) {
        super.setSoftwareStatementPayload(registrationRequest, decodedSSA);
    }

    @Override
    public String getRegistrationResponse(Map<String, Object> spMetaData) {
        return super.getRegistrationResponse(spMetaData);
    }
}
