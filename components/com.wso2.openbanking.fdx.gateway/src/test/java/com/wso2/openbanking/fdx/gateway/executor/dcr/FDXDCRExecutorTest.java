package com.wso2.openbanking.fdx.gateway.executor.dcr;

import com.wso2.openbanking.accelerator.common.config.OpenBankingConfigurationService;
import com.wso2.openbanking.accelerator.gateway.executor.dcr.DCRExecutor;
import com.wso2.openbanking.accelerator.gateway.executor.model.OBAPIRequestContext;
import com.wso2.openbanking.accelerator.gateway.internal.GatewayDataHolder;
import com.wso2.openbanking.fdx.gateway.util.FDXGatewayConstants;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.apimgt.common.gateway.dto.MsgInfoDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.HttpMethod;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


/**
 * Test for FDX DCR executor.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({OBAPIRequestContext.class, GatewayDataHolder.class})
public class FDXDCRExecutorTest {

    @Mock
    private GatewayDataHolder gatewayDataHolder;

    @Mock
    OpenBankingConfigurationService openBankingConfigurationService;

    @Test
    public void testInvalidInteractionIdHeader() {
        OBAPIRequestContext obapiRequestContext = mock(OBAPIRequestContext.class);
        MsgInfoDTO msgInfoDTO = new MsgInfoDTO();
        DCRExecutor dcrExecutor = spy(FDXDCRExecutor.class);

        msgInfoDTO.setHttpMethod(HttpMethod.GET);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(FDXGatewayConstants.INTERACTION_ID_HEADER, "765489876548650");
        msgInfoDTO.setHeaders(requestHeaders);
        Mockito.doReturn(msgInfoDTO).when(obapiRequestContext).getMsgInfo();

        dcrExecutor.preProcessRequest(obapiRequestContext);
        verify(obapiRequestContext).setError(true);

    }

    @Test
    public void testInteractionIdHeaderNotProvided() {
        OBAPIRequestContext obapiRequestContext = Mockito.mock(OBAPIRequestContext.class);
        MsgInfoDTO msgInfoDTO = new MsgInfoDTO();
        DCRExecutor dcrExecutor = Mockito.spy(FDXDCRExecutor.class);

        msgInfoDTO.setHttpMethod(HttpMethod.GET);
        Map<String, String> requestHeaders = new HashMap<>();
        msgInfoDTO.setHeaders(requestHeaders);
        Mockito.doReturn(msgInfoDTO).when(obapiRequestContext).getMsgInfo();

        dcrExecutor.preProcessRequest(obapiRequestContext);
        verify(obapiRequestContext).setError(true);
    }



    @Test
    public void testAddInteractionIdHeaderToResponseHeadersMap() {
        Map<String, Object> confMap = new HashMap<>();
        confMap.put("DCR.RequestJWTValidation", "false");
        Mockito.when(openBankingConfigurationService.getConfigurations()).thenReturn(confMap);
        Mockito.when(gatewayDataHolder.getOpenBankingConfigurationService())
                                        .thenReturn(openBankingConfigurationService);
        mockStatic(GatewayDataHolder.class);
        when(GatewayDataHolder.getInstance()).thenReturn(gatewayDataHolder);

        OBAPIRequestContext obapiRequestContext = Mockito.mock(OBAPIRequestContext.class);
        FDXDCRExecutor fdxdcrExecutor = Mockito.spy(new FDXDCRExecutor());

        String uuid = "770aef3-6784-41f7-8e0e-ff5f97bddb3";
        UUID expectedUuid = UUID.fromString(uuid);

        MsgInfoDTO msgInfoDTO = new MsgInfoDTO();
        msgInfoDTO.setHttpMethod(HttpMethod.GET);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(FDXGatewayConstants.INTERACTION_ID_HEADER, uuid);
        msgInfoDTO.setHeaders(requestHeaders);
        Mockito.doReturn(msgInfoDTO).when(obapiRequestContext).getMsgInfo();

        fdxdcrExecutor.preProcessRequest(obapiRequestContext);
        Assert.assertEquals(fdxdcrExecutor.responseHeaders.get(FDXGatewayConstants.INTERACTION_ID_HEADER),
                expectedUuid.toString());


    }


    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

}
