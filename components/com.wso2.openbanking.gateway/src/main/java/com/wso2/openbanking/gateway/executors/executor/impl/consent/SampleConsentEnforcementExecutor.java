/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Commercial License available at http://wso2.com/licenses. For specific
 * language governing the permissions and limitations under this license,
 * please see the license as well as any agreement you’ve entered into with
 * WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.openbanking.gateway.executors.executor.impl.consent;

import com.wso2.openbanking.accelerator.gateway.executor.impl.consent.ConsentEnforcementExecutor;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIRequestContext;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIResponseContext;
import org.json.JSONObject;

import java.util.Map;

/**
 * Consent Enforcement executor
 */
public class SampleConsentEnforcementExecutor extends ConsentEnforcementExecutor {

    @Override
    public void preProcessRequest(OBAPIRequestContext obapiRequestContext) {
        super.preProcessRequest(obapiRequestContext);
    }

    @Override
    public void preProcessResponse(OBAPIResponseContext obapiResponseContext) {
        super.preProcessResponse(obapiResponseContext);
    }

    @Override
    protected String generateJWT(String payload) {
        return super.generateJWT(payload);
    }

    @Override
    protected void handleError(OBAPIRequestContext obapiRequestContext, String errorCode, String errorMessage,
                               String httpCode) {
        super.handleError(obapiRequestContext, errorCode, errorMessage, httpCode);
    }

    @Override
    protected JSONObject createValidationRequestPayload(Map<String, String> requestHeaders, String requestPayload,
                                                        Map<String, Object> additionalParams) {
        return super.createValidationRequestPayload(requestHeaders, requestPayload, additionalParams);
    }

    @Override
    public void postProcessResponse(OBAPIResponseContext obapiResponseContext) {
        super.postProcessResponse(obapiResponseContext);
    }

    @Override
    public void postProcessRequest(OBAPIRequestContext obapiRequestContext) {
        super.postProcessRequest(obapiRequestContext);
    }
}
