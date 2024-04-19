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
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateDurationPeriod;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator class for validating the duration period when the duration type contains TIME_BOUND.
 */
public class DurationPeriodValidator implements ConstraintValidator<ValidateDurationPeriod, Object> {

    private static final Log log = LogFactory.getLog(DurationPeriodValidator.class);

    private String durationPeriodPath;
    private String durationTypePath;

    @Override
    public void initialize(ValidateDurationPeriod validateDurationPeriod) {
        this.durationPeriodPath = validateDurationPeriod.durationPeriodProperty();
        this.durationTypePath = validateDurationPeriod.durationTypeProperty();
    }

    @Override
    public boolean isValid(Object fdxRegistrationRequest, ConstraintValidatorContext constraintValidatorContext) {

        try {
            String durationPeriodStr = BeanUtils.getProperty(fdxRegistrationRequest, durationPeriodPath);
            String[] durationTypesArray = BeanUtils.getArrayProperty(fdxRegistrationRequest, durationTypePath);

            if (durationTypesArray != null) {
                List<String> durationTypes = Arrays.asList(durationTypesArray);
                if (durationTypes.contains(FDXDurationTypesEnum.TIME_BOUND.getValue())) {
                    if (StringUtils.isBlank(durationPeriodStr)) {
                        return false;
                    }
                }
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error while resolving validation fields", e);
            return false;
        }

        return true;
    }
}
