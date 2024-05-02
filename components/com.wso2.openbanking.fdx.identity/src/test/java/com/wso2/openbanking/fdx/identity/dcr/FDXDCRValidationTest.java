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

package com.wso2.openbanking.fdx.identity.dcr;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.wso2.openbanking.accelerator.common.constant.OpenBankingConstants;
import com.wso2.openbanking.accelerator.common.util.JWTUtils;
import com.wso2.openbanking.accelerator.common.util.OpenBankingUtils;
import com.wso2.openbanking.accelerator.identity.dcr.exception.DCRValidationException;
import com.wso2.openbanking.accelerator.identity.dcr.model.RegistrationRequest;
import com.wso2.openbanking.accelerator.identity.dcr.utils.ValidatorUtils;
import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.accelerator.identity.dcr.validation.RegistrationValidator;
import com.wso2.openbanking.accelerator.identity.internal.IdentityExtensionsDataHolder;

import com.wso2.openbanking.fdx.common.config.OpenBankingFDXConfigParser;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.model.RegistryReference;
import com.wso2.openbanking.fdx.identity.dcr.util.RegistrationTestConstants;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXRegistrationUtils;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXValidatorUtils;
import com.wso2.openbanking.fdx.identity.dcr.validation.FDXRegistrationValidatorImpl;
import com.wso2.openbanking.fdx.identity.testutils.IdentityTestDataProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.IObjectFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test Class for FDX DCR Validation class.
 */

@PrepareForTest({OpenBankingFDXConfigParser.class, ValidatorUtils.class, JWTUtils.class, OpenBankingUtils.class})
@PowerMockIgnore({"javax.net.ssl.*", "jdk.internal.reflect.*"})
public class FDXDCRValidationTest {

    private static final Log log = LogFactory.getLog(FDXDCRValidationTest.class);

    private RegistrationValidator registrationValidator;
    private RegistrationRequest registrationRequest;
    private final Map<String, Object> fdxConfigMap = new HashMap<>();

    private OpenBankingFDXConfigParser openBankingFDXConfigParser;

    private static final String NULL = "null";

    private static final String DCR_MAXIMUM_DURATION_PERIOD = "DCR.MaximumDurationPeriod";

    private static final String DCR_MAXIMUM_LOOKBACK_PERIOD = "DCR.MaximumLookbackPeriod";

    private static final String DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD = "DCR.DefaultTokenEndpointAuthMethod";

    private static final Gson gson = new Gson();

    @BeforeClass
    public void beforeClass() {
        Map<String, Object> confMap = new HashMap<>();
        Map<String, Map<String, Object>> dcrRegistrationConfMap = new HashMap<>();
        List<String> registrationParams = Arrays.asList("Issuer:false:null",
                "TokenEndPointAuthentication:false:null", "ResponseTypes:false:code id_token",
                "GrantTypes:false:null", "ApplicationType:false:web",
                "IdTokenSignedResponseAlg:true:null", "SoftwareStatement:false:null", "Scope:false:null");
        confMap.put(DCRCommonConstants.DCR_VALIDATOR, "com.wso2.openbanking.fdx.identity.dcr.validation" +
                ".FDXRegistrationValidatorImpl");
        confMap.put("DCR.JwksUrlProduction",
                "https://keystore.openbankingtest.org.uk/0015800001HQQrZAAX/oQ4KoaavpOuoE7rvQsZEOV.jwks");
        confMap.put("DCR.JwksUrlSandbox",
                "https://keystore.openbankingtest.org.uk/0015800001HQQrZAAX/oQ4KoaavpOuoE7rvQsZEOV.jwks");
        confMap.put("DCR.ModifyResponse", "true");
        fdxConfigMap.put("DCR.DefaultTokenEndpointAuthMethod", "tls_client_auth");
        List<String> validAlgorithms = new ArrayList<>();
        validAlgorithms.add("PS256");
        validAlgorithms.add("ES256");
        confMap.put(OpenBankingConstants.SIGNATURE_ALGORITHMS, validAlgorithms);
        IdentityExtensionsDataHolder.getInstance().setConfigurationMap(confMap);
        String dcrValidator = confMap.get(DCRCommonConstants.DCR_VALIDATOR).toString();
        registrationValidator = getDCRValidator(dcrValidator);
        registrationRequest = getRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        for (String param : registrationParams) {
            setParamConfig(param, dcrRegistrationConfMap);
        }
        IdentityExtensionsDataHolder.getInstance().setDcrRegistrationConfigMap(dcrRegistrationConfMap);

    }

    //Test for mandatory parameter: client name
    @Test(dataProvider = "nullAndEmpty", dataProviderClass = IdentityTestDataProvider.class)
    public void testClientNameExists(String clientName) {
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);

        fdxRegistrationRequest.setClientName(clientName);
        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Client Name cannot be null or empty"));
        }

    }

    //Test for mandatory parameter: redirect uris
    @Test(dataProvider = "nullAndEmptyArray", dataProviderClass = IdentityTestDataProvider.class)
    public void testRedirectURIsExist(List<String> redirectUris) {
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);

        fdxRegistrationRequest.setCallbackUris(redirectUris);
        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Redirect URIs can not be null or empty"));
        }
    }

    @Test(dataProvider = "nullAndEmpty", dataProviderClass = IdentityTestDataProvider.class)
    public void testInvalidRegisteredEntityName(String registeredEntityName) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        //set registered entity name to be null
        List<RegistryReference> registryReferences = fdxRegistrationRequest.getRegistryReferences();
        registryReferences.get(0).setRegisteredEntityName(registeredEntityName);

        fdxRegistrationRequest.setRegistryReferences(registryReferences);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription()
                    .contains("Registered Entity Name can not be null or empty in Registry References"));
        }

    }

    @Test(dataProvider = "nullAndEmpty", dataProviderClass = IdentityTestDataProvider.class)
    public void testInvalidRegisteredEntityId(String registeredEntityId) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        //set registered entity name to be null
        List<RegistryReference> registryReferences = fdxRegistrationRequest.getRegistryReferences();
        registryReferences.get(0).setRegisteredEntityId(registeredEntityId);

        fdxRegistrationRequest.setRegistryReferences(registryReferences);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription()
                    .contains("Registered Entity Id can not be null or empty in Registry References"));
        }

    }

    @Test(dataProvider = "nullAndEmpty", dataProviderClass = IdentityTestDataProvider.class)
    public void testInvalidRegistry(String registry) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        //set registered entity name to be null
        List<RegistryReference> registryReferences = fdxRegistrationRequest.getRegistryReferences();
        registryReferences.get(0).setRegistry(registry);
        fdxRegistrationRequest.setRegistryReferences(registryReferences);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription()
                    .contains("Registry can not be null or empty in Registry References"));
        }

    }



    @Test()
    public void testInvalidScope()  {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        String scope = "TAX PAYMENTS";
        fdxRegistrationRequest.setScope(scope);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Invalid scope requested"));
        }
    }

    @Test
    public void testInvalidDurationTypes()  {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        List<String> durationType = new ArrayList<>();
        durationType.add("any_time");
        fdxRegistrationRequest.setDurationType(durationType);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            log.error(e);
            Assert.assertTrue(e.getErrorDescription().contains("Invalid duration type requested"));
        }
    }


    @Test
    public void testInvalidDurationPeriod() {
        fdxConfigMap.put("DCR.MaximumDurationPeriod", "200");
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setDurationPeriod(300);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Duration period should not exceed 200 days"));
        } finally {
            fdxConfigMap.remove("DCR.MaximumDurationPeriod");
        }
    }

    @Test(dataProvider = "zeroAndNegative", dataProviderClass = IdentityTestDataProvider.class)
    public void testZeroOrNegativeDurationPeriod(Integer durationPeriod) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setDurationPeriod(durationPeriod);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Duration Period cannot be zero or negative"));
        }
    }


    @Test
    public void testInvalidDurationPeriodForTimeBoundDurationType() {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setDurationPeriod(null);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription()
                    .contains("Duration period is required for time_bound duration type"));
        }
    }

    @Test
    public void testInvalidLookbackPeriod() {
        fdxConfigMap.put("DCR.MaximumLookbackPeriod", "200");
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setLookbackPeriod(300);

        try {
            ValidatorUtils.getValidationViolations(fdxRegistrationRequest);
;
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Lookback period should not exceed 200 days"));
        } finally {
            fdxConfigMap.remove("DCR.MaximumLookbackPeriod");
        }

    }

    @Test(dataProvider = "grantTypes", dataProviderClass = IdentityTestDataProvider.class)
    public void testAddAllowedGrantTypes(List<String> grantTypes, List<String> expectedGrantTypes) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        registrationRequest = getRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);

        // set all request parameters to the registration request
        Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() { }.getType();
        Map<String, Object> requestParameters = gson.fromJson(RegistrationTestConstants.registrationRequestJson, type);
        registrationRequest.setRequestParameters(requestParameters);

        registrationRequest.setGrantTypes(grantTypes);
        FDXValidatorUtils.addAllowedGrantTypes(registrationRequest);

        //check if the registration request contains expected grant types
        Assert.assertEquals(registrationRequest.getGrantTypes(), expectedGrantTypes);


    }

    @Test(dataProvider = "tokenEndpointAuthMethods", dataProviderClass = IdentityTestDataProvider.class)
    public void testAddAllowedTokenEndpointAuthMethod(String authMethod, String expectedAuthMethod) {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        registrationRequest = getRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);

        // set all request parameters
        Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() { }.getType();
        Map<String, Object> requestParameters = gson.fromJson(RegistrationTestConstants.registrationRequestJson, type);
        registrationRequest.setRequestParameters(requestParameters);

        registrationRequest.setTokenEndPointAuthentication(authMethod);

        FDXValidatorUtils.addAllowedTokenEndpointAuthMethod(registrationRequest);

        //check if the registration request contains expected token endpoint auth method
        Assert.assertEquals(registrationRequest.getTokenEndPointAuthentication(), expectedAuthMethod);
    }

    @Test
    public void testResponseWithAddedAccessTokenAndClientUri() {
        String clientId = "AAAAAA";
        String tlsCert = "BBBBBB";
        String accessToken = "CCCCC";
        String clientUri = "https://localhost:8243/fdxv6.0.0recipientapi/6.0.0/register/";

        Map<String, Object> spMetadata = new HashMap<>();
        spMetadata.put("client_id", clientId);
        spMetadata.put("tls_cert", tlsCert);

        mockStatic(ValidatorUtils.class);
        when(ValidatorUtils.generateAccessToken(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(accessToken);
        when(ValidatorUtils.getRegistrationClientURI()).thenReturn(clientUri);

        String fdxRegistrationResponse = registrationValidator.getRegistrationResponse(spMetadata);

        // Assert that all expected values are present in the response
        List<String> expectedValues = Arrays.asList(
                String.format("\"registration_client_uri\":\"%s%s\"", clientUri, clientId),
                String.format("\"registration_access_token\":\"%s\"", accessToken)
        );
        Assert.assertTrue(expectedValues.stream().allMatch(fdxRegistrationResponse::contains));
    }


    //This test fails as signature validations are performed on the SSA from accelerator level,
    // which is not required for FDX ( request does not contain a SSA )
   /* @Test
    public void testValidatePost() throws Exception {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);

        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_DURATION_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_DURATION_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_MAXIMUM_LOOKBACK_PERIOD))
                .thenReturn(fdxConfigMap.get(DCR_MAXIMUM_LOOKBACK_PERIOD));
        when(openBankingFDXConfigParser.getConfiguration(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD))
                .thenReturn(fdxConfigMap.get(DCR_DEFAULT_TOKEN_ENDPOINT_AUTH_METHOD));

        registrationRequest = getRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);

        // set request parameters
        Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() { }.getType();
        Map<String, Object> requestParameters = gson.fromJson(RegistrationTestConstants.registrationRequestJson, type);
        registrationRequest.setRequestParameters(requestParameters);

        try {
            registrationValidator.validatePost(registrationRequest);
        } catch (DCRValidationException e) {
            Assert.fail("Exception was thrown" + e);
        }

    }*/

    @Test
    public void testGetJsonObjectsFromJsonStrings() {
        List<Object> spMetaData = new ArrayList<>();
        spMetaData.add(RegistrationTestConstants.registryReference);
        JsonObject registryReferenceJson = new JsonParser()
                                .parse(RegistrationTestConstants.registryReference).getAsJsonObject();
        FDXRegistrationUtils.getJsonObjectsFromJsonStrings(spMetaData);

        Assert.assertEquals(registryReferenceJson, spMetaData.get(0));
    }


    private static RegistrationRequest getRegistrationRequestObject(String request) {
        Gson gson = new Gson();
        return gson.fromJson(request, RegistrationRequest.class);

    }

    private static FDXRegistrationRequest getFDXRegistrationRequestObject(String request) {
        Gson gson = new Gson();
        return gson.fromJson(request, FDXRegistrationRequest.class);
    }

    public static RegistrationValidator getDCRValidator(String dcrValidator)  {
        if (StringUtils.isEmpty(dcrValidator)) {
            return new FDXRegistrationValidatorImpl();
        }
        try {
            return (RegistrationValidator) Class.forName(dcrValidator).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error instantiating " + dcrValidator, e);
            return new FDXRegistrationValidatorImpl();
        } catch (ClassNotFoundException e) {
            log.error("Cannot find class: " + dcrValidator, e);
            return new FDXRegistrationValidatorImpl();
        }
    }

    private void setParamConfig(String configParam, Map<String, Map<String, Object>> dcrRegistrationConfMap) {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put(DCRCommonConstants.DCR_REGISTRATION_PARAM_REQUIRED, configParam.split(":")[1]);
        if (!NULL.equalsIgnoreCase(configParam.split(":")[2])) {
            List<String> allowedValues = new ArrayList<>(Arrays.asList(configParam.split(":")[2].split(",")));
            parameterValues.put(DCRCommonConstants.DCR_REGISTRATION_PARAM_ALLOWED_VALUES, allowedValues);
        }
        dcrRegistrationConfMap.put(configParam.split(":")[0], parameterValues);
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }


}
