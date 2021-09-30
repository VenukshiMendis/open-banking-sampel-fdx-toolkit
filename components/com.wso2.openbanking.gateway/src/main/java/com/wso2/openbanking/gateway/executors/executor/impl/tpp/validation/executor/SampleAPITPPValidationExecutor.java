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
 *
 */

package com.wso2.openbanking.gateway.executors.executor.impl.tpp.validation.executor;

import com.wso2.openbanking.accelerator.gateway.executor.impl.tpp.validation.executor.APITPPValidationExecutor;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIRequestContext;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIResponseContext;

/**
 * TPP validation handler used to validate the TPP status using external validation
 * services for regular API requests
 */
public class SampleAPITPPValidationExecutor extends APITPPValidationExecutor {

    @Override
    public void preProcessRequest(OBAPIRequestContext obapiRequestContext) {
        super.preProcessRequest(obapiRequestContext);
    }

    @Override
    public void preProcessResponse(OBAPIResponseContext obapiResponseContext) {
        super.preProcessResponse(obapiResponseContext);
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
