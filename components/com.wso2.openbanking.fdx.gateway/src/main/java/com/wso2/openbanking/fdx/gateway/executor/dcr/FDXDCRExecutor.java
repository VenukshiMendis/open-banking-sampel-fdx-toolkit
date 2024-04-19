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

package com.wso2.openbanking.fdx.gateway.executor.dcr;

import com.wso2.openbanking.accelerator.gateway.executor.dcr.DCRExecutor;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIRequestContext;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIResponseContext;
import com.wso2.openbanking.accelerator.gateway.executor.model.OpenBankingExecutorError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * FDX Executor for signature validation, am app creation and API subscription for DCR.
 */
public class FDXDCRExecutor extends DCRExecutor {
    private static final Log log = LogFactory.getLog(FDXDCRExecutor.class);
    private static final String INTERACTION_ID_HEADER = "x-fapi-interaction-id";

    private final Map<String, String> addedHeaders = new HashMap<>();

    @Override
    public void preProcessRequest(OBAPIRequestContext obapiRequestContext) {
        if (obapiRequestContext.getMsgInfo().getHeaders().containsKey(INTERACTION_ID_HEADER)) {
            String interactionId = obapiRequestContext.getMsgInfo().getHeaders().get(INTERACTION_ID_HEADER);
            try {
                //Parse the interactionId as a UUID to validate of it's in the UUID format
                UUID uuid = UUID.fromString(interactionId);
                addedHeaders.put(INTERACTION_ID_HEADER, uuid.toString());
            } catch (IllegalArgumentException e) {
                log.error("Invalid interaction ID format. Must be a UUID.");
                handleBadRequestError(obapiRequestContext, e.getMessage());
            }

        } else {
            handleBadRequestError(obapiRequestContext, "Mandatory header x-fapi-interaction-id not provided");
        }

        super.preProcessRequest(obapiRequestContext);

    }

    @Override
    public void postProcessRequest(OBAPIRequestContext obapiRequestContext) {
        super.postProcessRequest(obapiRequestContext);

    }

    @Override
    public void preProcessResponse(OBAPIResponseContext obapiResponseContext) {
        obapiResponseContext.setAddedHeaders(addedHeaders);
        super.preProcessResponse(obapiResponseContext);
    }


    @Override
    public void postProcessResponse(OBAPIResponseContext obapiResponseContext) {
        super.postProcessResponse(obapiResponseContext);

    }

    private void handleBadRequestError(OBAPIRequestContext obapiRequestContext, String message) {
        //catch errors and set to context
        OpenBankingExecutorError error = new OpenBankingExecutorError("Bad request",
                "invalid_header_fields", message, "400");
        ArrayList<OpenBankingExecutorError> executorErrors = obapiRequestContext.getErrors();
        executorErrors.add(error);
        obapiRequestContext.setError(true);
        obapiRequestContext.setErrors(executorErrors);

    }
}
