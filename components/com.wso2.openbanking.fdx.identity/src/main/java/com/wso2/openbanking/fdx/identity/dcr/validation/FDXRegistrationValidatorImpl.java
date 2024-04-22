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

package com.wso2.openbanking.fdx.identity.dcr.validation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.wso2.openbanking.accelerator.identity.dcr.exception.DCRValidationException;
import com.wso2.openbanking.accelerator.identity.dcr.model.RegistrationRequest;
import com.wso2.openbanking.accelerator.identity.dcr.utils.ValidatorUtils;
import com.wso2.openbanking.accelerator.identity.dcr.validation.RegistrationValidator;
import com.wso2.openbanking.accelerator.identity.util.IdentityCommonConstants;
import com.wso2.openbanking.accelerator.identity.util.IdentityCommonUtil;
import com.wso2.openbanking.fdx.common.utils.CommonConstants;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationResponse;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXSoftwareStatementBody;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXRegistrationUtils;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXValidatorUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * FDX specific registration validator class..
 */
public class FDXRegistrationValidatorImpl extends RegistrationValidator {

    private static final Log log = LogFactory.getLog(FDXRegistrationValidatorImpl.class);
    private static final Gson gson = new Gson();
    @Override
    public void validatePost(RegistrationRequest registrationRequest) throws DCRValidationException {
        // add grant types and an authentication method to the registration request
        FDXValidatorUtils.addAllowedGrantTypes(registrationRequest);
        FDXValidatorUtils.addAllowedTokenEndpointAuthMethod(registrationRequest);

        //convert duration_period and lookback_period values to integers
        FDXRegistrationUtils.convertDoubleValueToInt(registrationRequest.getSsaParameters(),
                CommonConstants.DURATION_PERIOD);
        FDXRegistrationUtils.convertDoubleValueToInt(registrationRequest.getSsaParameters(),
                CommonConstants.LOOKBACK_PERIOD);

        // convert requestParameters in the registrationRequest to a fdxRegistrationRequest
        Map<String, Object> requestParameters = registrationRequest.getRequestParameters();
        JsonElement jsonElement = gson.toJsonTree(requestParameters);
        FDXRegistrationRequest fdxRegistrationRequest = gson.fromJson(jsonElement, FDXRegistrationRequest.class);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        FDXValidatorUtils.validateRequest(fdxRegistrationRequest);

    }

    @Override
    public void validateGet(String clientId) throws DCRValidationException {

    }

    @Override
    public void validateDelete(String clientId) throws DCRValidationException {

    }

    @Override
    public void validateUpdate(RegistrationRequest registrationRequest) throws DCRValidationException {

    }

    @Override
    public void setSoftwareStatementPayload(RegistrationRequest registrationRequest, String decodedSSA) {
        //assuming that FDX DCR Requests contain a software statement
        FDXSoftwareStatementBody fdxSoftwareStatementBody =  new GsonBuilder().create()
                .fromJson(decodedSSA, FDXSoftwareStatementBody.class);
        registrationRequest.setSoftwareStatementBody(fdxSoftwareStatementBody);

    }

    @Override
    public String getRegistrationResponse(Map<String, Object> spMetaData) {
        for (Map.Entry<String, Object> entry : spMetaData.entrySet()) {
            if (entry.getValue() instanceof ArrayList<?>) {
                ArrayList<Object> list = ((ArrayList<Object>) entry.getValue());
                //Convert JSON strings within the ArrayList to JSON objects
                FDXRegistrationUtils.getJsonObjectsFromJsonStrings(list);
            }
        }

        // Append registration access token and registration client URI to the DCR response if the config is enabled
        if (IdentityCommonUtil.getDCRModifyResponseConfig()) {
            String tlsCert = spMetaData.get(IdentityCommonConstants.TLS_CERT).toString();
            String clientId = spMetaData.get(IdentityCommonConstants.CLIENT_ID).toString();

            if (!spMetaData.containsKey(IdentityCommonConstants.REGISTRATION_ACCESS_TOKEN)) {
                // add the access token to the response
                spMetaData.put(IdentityCommonConstants.REGISTRATION_ACCESS_TOKEN,
                        ValidatorUtils.generateAccessToken(clientId, tlsCert));
            }

            // add the dcr url to the response with the client id appended at the end
            spMetaData.put(IdentityCommonConstants.REGISTRATION_CLIENT_URI,
                    ValidatorUtils.getRegistrationClientURI() + clientId);
        }

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(spMetaData);
        FDXRegistrationResponse fdxRegistrationResponse =
                gson.fromJson(jsonElement, FDXRegistrationResponse.class);
        return gson.toJson(fdxRegistrationResponse);
    }


}
