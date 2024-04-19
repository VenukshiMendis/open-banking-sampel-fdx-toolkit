/**
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wso2.openbanking.fdx.identity.dcr.validation.impl;

import com.wso2.openbanking.fdx.identity.dcr.constants.FDXValidationConstants;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXScopesEnum;
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateScopes;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



/**
 * Validator class for validating the scope.
 */

public class ScopesValidator implements ConstraintValidator<ValidateScopes, Object> {
    @Override
    public boolean isValid(Object scopes, ConstraintValidatorContext constraintValidatorContext) {

        if (scopes instanceof String) {
            String requestedScopes = (String) scopes;
            List<String> fdxScopes = FDXScopesEnum.getAllFDXScopes();

            for (String scope : requestedScopes.split("\\s+")) {
                boolean isFDXScope = fdxScopes.contains(scope);
                boolean isOpenID = FDXValidationConstants.OPENID.equals(scope);
                boolean isOfflineAccess = FDXValidationConstants.OFFLINE_ACCESS.equals(scope);

                if (!(isFDXScope || isOpenID || isOfflineAccess)) {
                    return false;
                }
            }

        }
        return true;
    }
}
