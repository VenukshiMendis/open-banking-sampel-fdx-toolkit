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

package com.wso2.openbanking.fdx.identity.dcr.utils;

import com.wso2.openbanking.accelerator.common.validator.OpenBankingValidator;
import com.wso2.openbanking.accelerator.identity.dcr.exception.DCRValidationException;
import com.wso2.openbanking.accelerator.identity.dcr.model.RegistrationRequest;
import com.wso2.openbanking.accelerator.identity.dcr.utils.ValidatorUtils;
import com.wso2.openbanking.accelerator.identity.dcr.validation.validationgroups.ValidationOrder;
import com.wso2.openbanking.fdx.common.config.OpenBankingFDXConfigParser;
import com.wso2.openbanking.fdx.identity.dcr.constants.FDXValidationConstants;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.model.RegistryReference;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;



/**
 * Util class for validation logic implementation.
 */
public class FDXValidatorUtils {


    public static void validateRequest(FDXRegistrationRequest  fdxRegistrationRequest) throws DCRValidationException {
        List<RegistryReference>  registryReferences = fdxRegistrationRequest.getRegistryReferences();

        //do registry reference validations
        if (registryReferences != null) {
            for (RegistryReference registryReference : registryReferences) {
                String error = OpenBankingValidator.getInstance()
                        .getFirstViolation(registryReference, ValidationOrder.class);
                if (error != null) {
                    String[] errors = error.split(":");
                    throw new DCRValidationException(errors[1], errors[0]);
                }
            }
        }

        //do validations related to registration request
        ValidatorUtils.getValidationViolations(fdxRegistrationRequest);

    }

    /**
     * Adds allowed grant types to the registration request based on the requested grant types.
     * If data recipient has not requested any grant types or none of the requested  grant types are allowed,
     * sets the allowed grant types.
     *
     * @param registrationRequest The registration request object.
     */
    public static void addAllowedGrantTypes(RegistrationRequest registrationRequest) {
        List<String> requestedGrantTypes = registrationRequest.getGrantTypes();
        List<String> allowedGrantTypes = AllowedGrantTypesEnum.getAllowedGrantTypes();

        // Determine the grant types to add based on the requested grant types
        List<String> grantTypesToAdd = (requestedGrantTypes == null || requestedGrantTypes.isEmpty()) ?
                allowedGrantTypes : requestedGrantTypes.stream()
                                    .filter(allowedGrantTypes::contains)
                                    .collect(Collectors.toList());

        // If none of the requested grant types are valid, set allowedGrantTypes as grant types to be added
        if (grantTypesToAdd.isEmpty()) {
            grantTypesToAdd = allowedGrantTypes;
        }

        registrationRequest.setGrantTypes(grantTypesToAdd);
        registrationRequest.getRequestParameters().put(FDXValidationConstants.GRANT_TYPES, grantTypesToAdd);

    }


    /**
     * Adds an allowed token endpoint authentication method based on the requested authentication method.
     * If the requested authentication method is blank or not allowed, sets the configured default method.
     *
     * @param registrationRequest The registration request object.
     */
    public static void addAllowedTokenEndpointAuthMethod(RegistrationRequest registrationRequest) {
        String requestedAuthMethod = registrationRequest.getTokenEndPointAuthentication();
        List<String> allowedAuthMethods = AllowedTokenEndPointAuthMethodsEnum.getAllowedAuthMethods();
        String authMethodToAdd;

        if (StringUtils.isBlank(requestedAuthMethod) || !allowedAuthMethods.contains(requestedAuthMethod)) {

            // Retrieve the default token endpoint authentication method from the configuration
            String tokenEndpointAuthMethodConfig = (String) OpenBankingFDXConfigParser.getInstance()
                    .getConfiguration().get(FDXValidationConstants.DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD);

            // Determine the authentication method to add, based on whether the default auth method is allowed
            authMethodToAdd = (allowedAuthMethods.contains(tokenEndpointAuthMethodConfig)) ?
                tokenEndpointAuthMethodConfig : AllowedTokenEndPointAuthMethodsEnum.PRIVATE_KEY_JWT.getValue();

            registrationRequest.setTokenEndPointAuthentication(authMethodToAdd);
            registrationRequest.getRequestParameters()
                    .put(FDXValidationConstants.TOKEN_ENDPOINT_AUTH_METHOD, authMethodToAdd);

        }
    }

}
