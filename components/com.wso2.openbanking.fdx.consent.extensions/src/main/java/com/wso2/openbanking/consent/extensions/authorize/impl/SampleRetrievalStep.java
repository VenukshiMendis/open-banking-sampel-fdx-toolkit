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

package com.wso2.openbanking.consent.extensions.authorize.impl;
import com.wso2.openbanking.accelerator.common.util.DatabaseUtil;
import com.wso2.openbanking.accelerator.consent.extensions.authorize.impl.DefaultConsentRetrievalStep;
import com.wso2.openbanking.accelerator.consent.extensions.authorize.model.ConsentData;
import com.wso2.openbanking.accelerator.consent.extensions.authorize.model.ConsentRetrievalStep;
import com.wso2.openbanking.accelerator.consent.extensions.common.ConsentException;
import com.wso2.openbanking.accelerator.consent.extensions.common.ResponseStatus;
import com.wso2.openbanking.accelerator.consent.mgt.dao.exceptions.OBConsentDataRetrievalException;
import com.wso2.openbanking.accelerator.consent.mgt.dao.impl.ConsentCoreDAOImpl;
import com.wso2.openbanking.accelerator.consent.mgt.dao.models.ConsentAttributes;
import com.wso2.openbanking.accelerator.consent.mgt.dao.queries.ConsentMgtCommonDBQueries;
import com.wso2.openbanking.accelerator.identity.push.auth.extension.request.validator.exception.PushAuthRequestValidatorException;
import com.wso2.openbanking.accelerator.identity.push.auth.extension.request.validator.util.PushAuthRequestValidatorUtils;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.cache.SessionDataCache;
import org.wso2.carbon.identity.oauth.cache.SessionDataCacheEntry;
import org.wso2.carbon.identity.oauth.cache.SessionDataCacheKey;


import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Consent retrieval step sample implementation.
 */
public class SampleRetrievalStep implements ConsentRetrievalStep {
    private static final Log log = LogFactory.getLog(SampleRetrievalStep.class);
    private static final String[] CLAIM_FIELDS = new String[]{"userinfo", "id_token"};

    @Override
    public void execute(ConsentData consentData, JSONObject jsonObject) throws ConsentException {
        //Homework
        if (consentData.isRegulatory()) {
            //Get ClientId from the JWT object passed in the request
            String consentId = consentData.getConsentId();
            if (consentId == null) {
                if (consentData.getSpQueryParams() == null) {
                    throw new ConsentException(ResponseStatus.BAD_REQUEST,
                            "CIBA consent retrieval step has not been executed successfully before " +
                                    "default consent persist step");
                }

                String requestObject = this.extractRequestObject(consentData.getSpQueryParams());
                consentId = this.validateRequestObjectAndExtractConsentId(requestObject);

                //Get is_one_off_consent value(True/False) related to the given consentId
                Connection connection = DatabaseUtil.getDBConnection();
                ConsentMgtCommonDBQueries consentMgtCommonDBQueries = new ConsentMgtCommonDBQueries();
                ConsentCoreDAOImpl  consentCoreDAO = new ConsentCoreDAOImpl(consentMgtCommonDBQueries);
                ArrayList<String> attributes = new ArrayList<>();
                attributes.add("is_one_off_consent");
                ConsentAttributes consentAttributes;
                //getConsentAttributes --> get ATT_VALUE by giving consent id and attribute key.
               try {
                  consentAttributes =
                           consentCoreDAO.getConsentAttributes(connection, consentId, attributes);
               } catch (OBConsentDataRetrievalException e) {
                    throw new RuntimeException(e);
               }
                Map<String, String> consentAttributesMap = consentAttributes.getConsentAttributes();

                String isOneOffConsent = consentAttributesMap.get("is_one_off_consent");

                //Using consentAttributes check if is_one_off_consent=False
                if (isOneOffConsent.equals("false")) {
                    //Check if user has other one_of_consents(False) from the same application
                    try {
                        //"admin@wso2.com@carbon.super"
                        String userId = consentData.getUserId();
                        String clientId = consentData.getClientId();
                        int oneOffCount = getOneOffConsentsOfUser(connection, userId, clientId);
                        if (oneOffCount > 0) {
                            log.error("One off consent limit reached");
                            throw new ConsentException(ResponseStatus.FORBIDDEN, "One off consent limit reached");
                        }
                    } catch (OBConsentDataRetrievalException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        }


        DefaultConsentRetrievalStep defaultConsentRetrievalStep = new DefaultConsentRetrievalStep();
        defaultConsentRetrievalStep.execute(consentData, jsonObject);
    }

    private int getOneOffConsentsOfUser(Connection connection, String userId, String clientId)
            throws OBConsentDataRetrievalException {
        Map<String, String> retrievedConsentAttributesMap = new HashMap<>();
        String getConsentAttributesPrepStatement = "SELECT count(*) " +
                "as one_off_consents FROM ob_consent_attribute join " +
                "ob_consent_auth_resource using (consent_id) join " +
                "ob_consent using(consent_id) where ATT_KEY = ? and " +
                "ATT_VALUE = ? and USER_ID = ? and CLIENT_ID = ?";

        try {
            PreparedStatement getConsentAttributesPreparedStmt =
                    connection.prepareStatement(getConsentAttributesPrepStatement);
            Throwable var7 = null;

            try {
                log.debug("Setting parameters to prepared statement to retrieve consent attributes");
                getConsentAttributesPreparedStmt.setString(1, "is_one_off_consent");
                getConsentAttributesPreparedStmt.setString(2, "false");
                getConsentAttributesPreparedStmt.setString(3, userId);
                getConsentAttributesPreparedStmt.setString(4, clientId);

                try {
                    ResultSet resultSet = getConsentAttributesPreparedStmt.executeQuery();
                    Throwable var9 = null;
                    if (!resultSet.isBeforeFirst()) {
                        log.error("SQL error when retrieving one_off_consent count");
                        throw new OBConsentDataRetrievalException("SQL error when retrieving one_off_consent count");
                    } else {
                        if (resultSet.next()) {
                            //log.info(noOfOneOffConsents);
                           return resultSet.getInt("one_off_consents");
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }
    private String extractRequestObject(String spQueryParams) {
        if (spQueryParams != null && !spQueryParams.trim().isEmpty()) {
            String requestObject = null;
            String[] spQueries = spQueryParams.split("&");
            String clientId = null;
            String[] var5 = spQueries;
            int var6 = spQueries.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String param = var5[var7];
                if (param.contains("client_id=")) {
                    clientId = param.split("client_id=")[1];
                }

                if (param.contains("request=")) {
                    requestObject = param.substring("request=".length()).replaceAll("\\r\\n|\\r|\\n|\\%20", "");
                } else if (param.contains("request_uri=")) {
                    log.debug("Resolving request URI during Steps execution");
                    String[] requestUri = param.substring("request_uri=".length()).replaceAll("\\%3A", ":").split(":");
                    String sessionKey = requestUri[requestUri.length - 1];
                    SessionDataCacheKey cacheKey = new SessionDataCacheKey(sessionKey);
                    SessionDataCacheEntry sessionDataCacheEntry =
                            SessionDataCache.getInstance().getValueFromCache(cacheKey);
                    if (sessionDataCacheEntry == null) {
                        log.error("Could not find cache entry with request URI");
                        throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                                "Request object cannot be extracted");
                    }

                    String requestObjectFromCache = sessionDataCacheEntry.
                            getoAuth2Parameters().getEssentialClaims().split(":")[0];
                    if (requestObjectFromCache.split("\\.").length == 5) {
                        try {
                            requestObject = PushAuthRequestValidatorUtils.decrypt(requestObjectFromCache, clientId);
                        } catch (PushAuthRequestValidatorException var15) {
                            log.error("Error occurred while decrypting", var15);
                            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                                    "Request object cannot be extracted");
                        }
                    } else {
                        requestObject = requestObjectFromCache;
                    }

                    log.debug("Removing request_URI entry from cache");
                    SessionDataCache.getInstance().clearCacheEntry(cacheKey);
                }
            }

            if (requestObject != null) {
                return requestObject;
            }
        }

        throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Request object cannot be extracted");
    }

    private String validateRequestObjectAndExtractConsentId(String requestObject) {
        String consentId = null;

        try {
            String[] jwtTokenValues = requestObject.split("\\.");
            if (jwtTokenValues.length == 3) {
                String requestObjectPayload =
                        new String(Base64.getUrlDecoder().decode(jwtTokenValues[1]), StandardCharsets.UTF_8);
                Object payload = (new JSONParser(-1)).parse(requestObjectPayload);
                if (!(payload instanceof JSONObject)) {
                    throw new ConsentException(ResponseStatus.BAD_REQUEST, "Payload is not a JSON object");
                } else {
                    JSONObject jsonObject = (JSONObject) payload;
                    if (jsonObject.containsKey("claims")) {
                        JSONObject claims = (JSONObject) jsonObject.get("claims");
                        String[] var8 = CLAIM_FIELDS;
                        int var9 = var8.length;

                        for (int var10 = 0; var10 < var9; ++var10) {
                            String claim = var8[var10];
                            if (claims.containsKey(claim)) {
                                JSONObject claimObject = (JSONObject) claims.get(claim);
                                if (claimObject.containsKey("openbanking_intent_id")) {
                                    JSONObject intentObject = (JSONObject) claimObject.get("openbanking_intent_id");
                                    if (intentObject.containsKey("value")) {
                                        consentId = (String) intentObject.get("value");
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (consentId == null) {
                        log.error("intent_id not found in request object");
                        throw new ConsentException(ResponseStatus.BAD_REQUEST, "intent_id not found in request object");
                    } else {
                        return consentId;
                    }
                }
            } else {
                log.error("request object is not signed JWT");
                throw new ConsentException(ResponseStatus.BAD_REQUEST, "request object is not signed JWT");
            }
        } catch (ParseException var14) {
            log.error("Error while parsing the request object : ", var14);
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Error while parsing the request object ");
        }
    }


}
