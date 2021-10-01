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

package com.wso2.openbanking.identity.auth.extensions.request.validator;

import com.wso2.openbanking.accelerator.identity.auth.extensions.request.validator.DefaultOBRequestObjectValidator;
import com.wso2.openbanking.accelerator.identity.auth.extensions.request.validator.OBRequestObjectValidator;
import com.wso2.openbanking.accelerator.identity.auth.extensions.request.validator.models.OBRequestObject;
import com.wso2.openbanking.accelerator.identity.auth.extensions.request.validator.models.ValidationResponse;

import java.util.Map;

/**
 * Sample extension class for enforcing Request Object Validations.
 */
public class SampleRequestObjectValidator extends OBRequestObjectValidator {

    DefaultOBRequestObjectValidator defaultOBRequestObjectValidator = new DefaultOBRequestObjectValidator();

    @Override
    public ValidationResponse validateOBConstraints(OBRequestObject obRequestObject, Map<String, Object> dataMap) {

        return defaultOBRequestObjectValidator.validateOBConstraints(obRequestObject, dataMap);
    }
}
