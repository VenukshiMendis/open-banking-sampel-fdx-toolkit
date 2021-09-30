/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Commercial License available at http://wso2.com/licenses.
 * For specific language governing the permissions and limitations under this
 * license, please see the license as well as any agreement you’ve entered into
 * with WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.openbanking.gateway.executors.executor.impl.api.resource.access.validation;

import com.wso2.openbanking.accelerator.gateway.executor.exception.OpenBankingExecutorException;
import com.wso2.openbanking.accelerator.gateway.executor.impl.api.resource.access.validation.APIResourceAccessValidationExecutor;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIRequestContext;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIResponseContext;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;
import java.util.Map;

/**
 * API Resource Access Validation executor
 * This executor validates the grant type
 */
public class SampleAPIResourceAccessValidationExecutor extends APIResourceAccessValidationExecutor {

    @Override
    public void preProcessRequest(OBAPIRequestContext obapiRequestContext) {
        super.preProcessRequest(obapiRequestContext);
    }

    @Override
    public void preProcessResponse(OBAPIResponseContext obapiResponseContext) {
        super.preProcessResponse(obapiResponseContext);
    }

    @Override
    protected List<String> getAllowedOAuthFlowsFromSwagger(OpenAPI openAPI, String electedResource, String httpMethod) {
        return super.getAllowedOAuthFlowsFromSwagger(openAPI, electedResource, httpMethod);
    }

    @Override
    protected String getTokenType(String tokenPayload) {
        return super.getTokenType(tokenPayload);
    }

    @Override
    protected String getConsentIdClaimName() {
        return super.getConsentIdClaimName();
    }

    @Override
    protected String getBearerTokenPayload(Map<String, String> transportHeaders) throws OpenBankingExecutorException {
        return super.getBearerTokenPayload(transportHeaders);
    }

    @Override
    protected void validateGrantType(String tokenType, List<String> allowedOAuthFlows)
            throws OpenBankingExecutorException {
        super.validateGrantType(tokenType, allowedOAuthFlows);
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
