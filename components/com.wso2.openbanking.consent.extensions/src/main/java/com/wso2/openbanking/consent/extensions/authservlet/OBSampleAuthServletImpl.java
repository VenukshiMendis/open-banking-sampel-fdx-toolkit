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

package com.wso2.openbanking.consent.extensions.authservlet;

import com.wso2.openbanking.accelerator.consent.extensions.authservlet.impl.OBDefaultAuthServletImpl;
import com.wso2.openbanking.accelerator.consent.extensions.authservlet.model.OBAuthServletInterface;
import org.json.JSONObject;

import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

/**
 * The Sample implementation of servlet extension
 */
public class OBSampleAuthServletImpl implements OBAuthServletInterface {

    OBDefaultAuthServletImpl obDefaultAuthServlet = new OBDefaultAuthServletImpl();

    @Override
    public Map<String, Object> updateRequestAttribute(HttpServletRequest httpServletRequest, JSONObject jsonObject,
                                                      ResourceBundle resourceBundle) {
        return obDefaultAuthServlet.updateRequestAttribute(httpServletRequest, jsonObject, resourceBundle);
    }

    @Override
    public Map<String, Object> updateSessionAttribute(HttpServletRequest httpServletRequest, JSONObject jsonObject,
                                                      ResourceBundle resourceBundle) {
        return obDefaultAuthServlet.updateSessionAttribute(httpServletRequest, jsonObject, resourceBundle);
    }

    @Override
    public Map<String, Object> updateConsentData(HttpServletRequest httpServletRequest) {
        return obDefaultAuthServlet.updateConsentData(httpServletRequest);
    }

    @Override
    public Map<String, String> updateConsentMetaData(HttpServletRequest httpServletRequest) {
        return obDefaultAuthServlet.updateConsentMetaData(httpServletRequest);
    }

    @Override
    public String getJSPPath() {
        return obDefaultAuthServlet.getJSPPath();
    }
}
