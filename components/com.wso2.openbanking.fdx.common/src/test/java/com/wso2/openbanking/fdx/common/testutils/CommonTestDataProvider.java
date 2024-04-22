package com.wso2.openbanking.fdx.common.testutils;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Provider FDX CDS Common Tests.
 */
public class CommonTestDataProvider {

    @DataProvider(name = "dcrConfigs")
    public Object[][] dcrConfigsTestDataProvider() {
        return new Object[][] {
                {"DCR.MaximumDurationPeriod", "200"},
                {"DCR.MaximumLookbackPeriod", "356"},
                {"DCR.TokenEndpointAuthMethod", "tls_client_auth"}
        };
    }

    @DataProvider(name = "sampleArrayConfig")
    public Object[][] sampleArrayConfigTestDataProvider() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("DummyValue1");
        expectedList.add("DummyValue2");
        expectedList.add("DummyValue3");

        return new Object[][] {
                {"SampleArray.RepeatableTag", expectedList }
        };

    }
}
