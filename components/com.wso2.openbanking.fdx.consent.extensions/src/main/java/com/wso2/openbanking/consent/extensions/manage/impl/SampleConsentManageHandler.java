/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Software License available at https://wso2.com/licenses/eula/3.1.
 * For specific language governing the permissions and limitations under this
 * license, please see the license as well as any agreement you’ve entered into
 * with WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.openbanking.consent.extensions.manage.impl;

import com.wso2.openbanking.accelerator.common.exception.ConsentManagementException;
import com.wso2.openbanking.accelerator.consent.extensions.common.ConsentException;
import com.wso2.openbanking.accelerator.consent.extensions.manage.impl.DefaultConsentManageHandler;
import com.wso2.openbanking.accelerator.consent.extensions.manage.model.ConsentManageData;
import com.wso2.openbanking.accelerator.consent.extensions.manage.model.ConsentManageHandler;
import com.wso2.openbanking.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.util.HashMap;
import java.util.Map;

/**
 * Consent Manage handler implementation for .
 */
public class SampleConsentManageHandler implements ConsentManageHandler {
    private static final Log log = LogFactory.getLog(SampleConsentManageHandler.class);
    DefaultConsentManageHandler defaultConsentManageHandler = new DefaultConsentManageHandler();

    @Override
    public void handleFileUploadPost(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handleFileUploadPost(consentManageData);
    }

    @Override
    public void handleFileGet(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handleFileGet(consentManageData);
    }

    @Override
    public void handleGet(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handleGet(consentManageData);
    }

    @Override
    public void handlePost(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handlePost(consentManageData);
        ConsentCoreServiceImpl service = new ConsentCoreServiceImpl();
       // String consentId =(String)
        // ((JSONObject)((JSONObject) consentManageData.getResponsePayload()).get("Data")).get("ConsentId");
        String consentId = getAttributesFromResponsePayload(consentManageData, "ConsentId");
        Boolean isOneOffConsent = getAttributesFromResponsePayload(consentManageData, "is_one_off_consent");
        Map<String, String> values = new HashMap<>();
        values.put("is_one_off_consent", isOneOffConsent.toString());
        try {
            service.storeConsentAttributes(consentId, values);
            log.info("Attributes written to db successfully");
        } catch (ConsentManagementException e) {
            log.error(e);
        }
    }

    @Override
    public void handleDelete(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handleDelete(consentManageData);
    }

    @Override
    public void handlePut(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handlePut(consentManageData);
    }

    @Override
    public void handlePatch(ConsentManageData consentManageData) throws ConsentException {
        defaultConsentManageHandler.handlePatch(consentManageData);
    }

    //get consentId and is_one_off_consent values from consentManageData
    private <T> T getAttributesFromResponsePayload(ConsentManageData consentManageData, String key) {
        JSONObject responsePayload = (JSONObject) consentManageData.getResponsePayload();
        JSONObject data = (JSONObject) responsePayload.get("Data");
        return (T) data.get(key);
    }
}
