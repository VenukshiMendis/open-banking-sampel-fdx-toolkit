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

import com.wso2.openbanking.fdx.identity.dcr.utils.FDXDurationTypesEnum;
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateDurationType;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator class for duration type validation.
 */
public class DurationTypeValidator implements ConstraintValidator<ValidateDurationType, Object> {
    @Override
    public boolean isValid(Object durationTypes, ConstraintValidatorContext constraintValidatorContext) {

        if (durationTypes instanceof List) {
            List<?> requestedDurationTypes = (List<?>) durationTypes;
            List<String> allowedDurationTypes = FDXDurationTypesEnum.getAllDurationTypes();

            for (Object durationTypeObj: requestedDurationTypes) {
                if (durationTypeObj instanceof String) {
                    String durationType = (String) durationTypeObj;
                    if (!allowedDurationTypes.contains(durationType)) {
                       return false;
                    }
                } else {
                    return false;
                }

            }
        }
        return true;
    }
}
