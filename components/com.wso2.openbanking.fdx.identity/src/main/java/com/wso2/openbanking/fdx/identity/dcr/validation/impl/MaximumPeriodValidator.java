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

import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.fdx.common.config.OpenBankingFDXConfigParser;
import com.wso2.openbanking.fdx.identity.dcr.constants.FDXValidationConstants;
import com.wso2.openbanking.fdx.identity.dcr.validation.annotation.ValidateMaximumPeriod;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator class for validating the Duration Period and Lookback Period parameters.
 */


public class MaximumPeriodValidator implements ConstraintValidator<ValidateMaximumPeriod, Object> {

    private static final Log log = LogFactory.getLog(MaximumPeriodValidator.class);

    private String durationPeriodPath;
    private String lookbackPeriodPath;

    @Override
    public void initialize(ValidateMaximumPeriod validateMaximumPeriod) {
        this.durationPeriodPath = validateMaximumPeriod.durationPeriodProperty();
        this.lookbackPeriodPath = validateMaximumPeriod.lookbackPeriodProperty();

    }

    @Override
    public boolean isValid(Object fdxRegistrationRequest, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Map<String, Object> configurationsMap = OpenBankingFDXConfigParser.getInstance().getConfiguration();

            //get maximum duration period and maximum lookback period from configs
            String maximumDurationPeriodStr = (String) configurationsMap.get(
                    FDXValidationConstants.DCR_MAXIMUM_DURATION_PERIOD);
            String maximumLookbackPeriodStr = (String) configurationsMap.get(
                    FDXValidationConstants.DCR_MAXIMUM_LOOKBACK_PERIOD);

            //get duration period and lookback period from registration request
            String durationPeriodStr = BeanUtils.getProperty(fdxRegistrationRequest, durationPeriodPath);
            String lookbackPeriodStr = BeanUtils.getProperty(fdxRegistrationRequest, lookbackPeriodPath);

            //validate duration period
            if (!validatePeriod(durationPeriodStr, maximumDurationPeriodStr, "Duration period" ,
                    constraintValidatorContext)) {
                return false;
            }

            //validate look back period
            if (!validatePeriod (lookbackPeriodStr, maximumLookbackPeriodStr, "Lookback period",
                    constraintValidatorContext)) {
                return false;
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error while resolving validation fields", e);
            return false;
        }

        return true;
    }

    //TODO : Change max period comparison to integers
    private boolean validatePeriod(String periodStr, String maximumPeriodStr, String periodName,
                                   ConstraintValidatorContext context) {
       try {
           //check whether maximum period value is provided as configs
           if (periodStr != null && maximumPeriodStr != null) {
               int period = Integer.parseInt(periodStr);
               int maximumPeriod = Integer.parseInt(maximumPeriodStr);
               if (period > maximumPeriod) {
                   context.disableDefaultConstraintViolation();
                   context.buildConstraintViolationWithTemplate(periodName + " should not exceed " +
                                   maximumPeriod + " days :" + DCRCommonConstants.INVALID_META_DATA)
                           .addConstraintViolation();
                   return false;
               }
           }
       } catch (NumberFormatException e) {
           log.error("Error while resolving configs", e);
           return false;
       }

       return true;
    }





}
