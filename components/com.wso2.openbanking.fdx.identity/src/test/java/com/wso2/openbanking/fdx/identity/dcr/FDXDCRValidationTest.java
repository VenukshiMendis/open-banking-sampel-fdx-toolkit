package com.wso2.openbanking.fdx.identity.dcr;

import com.google.gson.Gson;

import com.wso2.openbanking.accelerator.common.constant.OpenBankingConstants;
import com.wso2.openbanking.accelerator.common.util.JWTUtils;
import com.wso2.openbanking.accelerator.identity.dcr.exception.DCRValidationException;
import com.wso2.openbanking.accelerator.identity.dcr.model.RegistrationRequest;
import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.accelerator.identity.dcr.validation.RegistrationValidator;
import com.wso2.openbanking.accelerator.identity.internal.IdentityExtensionsDataHolder;

import com.wso2.openbanking.fdx.common.config.OpenBankingFDXConfigParser;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXRegistrationRequest;
import com.wso2.openbanking.fdx.identity.dcr.model.FDXSoftwareStatementBody;
import com.wso2.openbanking.fdx.identity.dcr.util.RegistrationTestConstants;
import com.wso2.openbanking.fdx.identity.dcr.utils.FDXValidatorUtils;
import com.wso2.openbanking.fdx.identity.dcr.validation.FDXRegistrationValidatorImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.text.ParseException;
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

@PrepareForTest({OpenBankingFDXConfigParser.class})
@PowerMockIgnore({"javax.net.ssl.*", "jdk.internal.reflect.*"})
public class FDXDCRValidationTest {

    private static final Log log = LogFactory.getLog(FDXDCRValidationTest.class);

    private RegistrationValidator registrationValidator;
    private RegistrationRequest registrationRequest;
    private final Map<String, Object> fdxConfigMap = new HashMap<>();

    private OpenBankingFDXConfigParser openBankingFDXConfigParser;

    private static final String NULL = "null";

    private static final Gson gson = new Gson();

    @BeforeClass
    public void beforeClass() {
        Map<String, Object> confMap = new HashMap<>();
        Map<String, Map<String, Object>> dcrRegistrationConfMap = new HashMap<>();
        List<String> registrationParams = Arrays.asList("Issuer:true:null",
                "TokenEndPointAuthentication:false:null", "ResponseTypes:false:code id_token",
                "GrantTypes:false:null", "ApplicationType:false:web",
                "IdTokenSignedResponseAlg:true:null", "SoftwareStatement:true:null", "Scope:false:null");
        confMap.put(DCRCommonConstants.DCR_VALIDATOR, "com.wso2.openbanking.fdx.identity.dcr.validation" +
                ".FDXRegistrationValidatorImpl");
        confMap.put("DCR.JwksUrlProduction",
                "https://keystore.openbankingtest.org.uk/0015800001HQQrZAAX/oQ4KoaavpOuoE7rvQsZEOV.jwks");
        confMap.put("DCR.JwksUrlSandbox",
                "https://keystore.openbankingtest.org.uk/0015800001HQQrZAAX/oQ4KoaavpOuoE7rvQsZEOV.jwks");
        fdxConfigMap.put("DCR.DefaultTokenEndpointAuthMethod", "private_key_jwt");
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
    @Test
    public void testClientNameExists() {
        String decodedSSA = null;
        try {
            decodedSSA = JWTUtils
                    .decodeRequestJWT(registrationRequest.getSoftwareStatement(), "body").toJSONString();
        } catch (ParseException e) {
            log.error("Error while parsing the SSA", e);
        }

        registrationValidator.setSoftwareStatementPayload(registrationRequest, decodedSSA);
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        // Set client name to null
        fdxRegistrationRequest.setClientName(null);
        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Client Name cannot be null or empty"));
        }

        //set client name empty
        fdxRegistrationRequest.setClientName("");
        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Client Name cannot be null or empty"));
        }
    }

    //Test for mandatory parameter: redirect uris
    @Test
    public void testRedirectURIsExist() {
        String decodedSSA = null;
        try {
            decodedSSA = JWTUtils
                    .decodeRequestJWT(registrationRequest.getSoftwareStatement(), "body").toJSONString();
        } catch (ParseException e) {
            log.error("Error while parsing the SSA", e);
        }

        registrationValidator.setSoftwareStatementPayload(registrationRequest, decodedSSA);
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        // Set redirect uris to null
        fdxRegistrationRequest.setCallbackUris(null);
        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Redirect URIs can not be null or empty"));
        }

        //set redirect uris empty
        fdxRegistrationRequest.setCallbackUris(new ArrayList<>());
        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().
                    contains("Required parameter Redirect URIs can not be null or empty"));
        }
    }


    @Test
    public void testInvalidScope() throws Exception {
        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);
        when(openBankingFDXConfigParser.getConfiguration()).thenReturn(fdxConfigMap);

        String decodedSSA = null;
        try {
            decodedSSA = JWTUtils
                    .decodeRequestJWT(registrationRequest.getSoftwareStatement(), "body").toJSONString();
        } catch (ParseException e) {
            log.error("Error while parsing the SSA", e);
        }

        registrationValidator.setSoftwareStatementPayload(registrationRequest, decodedSSA);

        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        fdxRegistrationRequest.setScope("TAX PAYMENTS");
        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Invalid scope requested"));
        }
    }

    @Test
    public void testInvalidDurationTypes() throws Exception {
        //fdxConfigMap.put("DCR.MaximumDurationPeriod", "200");

        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);
        when(openBankingFDXConfigParser.getConfiguration()).thenReturn(fdxConfigMap);

        String decodedSSA = null;
        try {
            decodedSSA = JWTUtils
                    .decodeRequestJWT(registrationRequest.getSoftwareStatement(), "body").toJSONString();
        } catch (ParseException e) {
            log.error("Error while parsing the SSA", e);
        }

        registrationValidator.setSoftwareStatementPayload(registrationRequest, decodedSSA);
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        List<String> durationType = new ArrayList<>();
        durationType.add("any_time");
        fdxRegistrationRequest.setDurationType(durationType);

        //Request parameters need to be matched with SSA parameters
        FDXSoftwareStatementBody ssaBody =
                      (FDXSoftwareStatementBody) fdxRegistrationRequest.getSoftwareStatementBody();
        ssaBody.setDurationType(durationType);
        fdxRegistrationRequest.setSoftwareStatementBody(ssaBody);

        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Invalid duration type requested"));
        }
    }


    @Test
    public void testInvalidDurationPeriod() throws Exception {
        fdxConfigMap.put("DCR.MaximumDurationPeriod", "200");

        mockStatic(OpenBankingFDXConfigParser.class);
        openBankingFDXConfigParser = mock(OpenBankingFDXConfigParser.class);
        when(OpenBankingFDXConfigParser.getInstance()).thenReturn(openBankingFDXConfigParser);
        when(openBankingFDXConfigParser.getConfiguration()).thenReturn(fdxConfigMap);

        String decodedSSA = null;
        try {
            decodedSSA = JWTUtils
                    .decodeRequestJWT(registrationRequest.getSoftwareStatement(), "body").toJSONString();
        } catch (ParseException e) {
            log.error("Error while parsing the SSA", e);
        }

        registrationValidator.setSoftwareStatementPayload(registrationRequest, decodedSSA);
        FDXRegistrationRequest fdxRegistrationRequest =
                getFDXRegistrationRequestObject(RegistrationTestConstants.registrationRequestJson);
        fdxRegistrationRequest.setSoftwareStatementBody(registrationRequest.getSoftwareStatementBody());

        fdxRegistrationRequest.setDurationPeriod(300);

        //Request parameters need to be matched with SSA parameters
        FDXSoftwareStatementBody ssaBody =
              (FDXSoftwareStatementBody) fdxRegistrationRequest.getSoftwareStatementBody();
        ssaBody.setDurationPeriod(300);
        fdxRegistrationRequest.setSoftwareStatementBody(ssaBody);

        try {
            FDXValidatorUtils.validateRequest(fdxRegistrationRequest);
        } catch (DCRValidationException e) {
            Assert.assertTrue(e.getErrorDescription().contains("Duration period should not exceed 200 days"));
        }

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
