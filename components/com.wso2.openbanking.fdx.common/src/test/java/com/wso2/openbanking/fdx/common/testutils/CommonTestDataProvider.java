<!--
  ~ Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
  ~
  ~ This software is the property of WSO2 Inc. and its suppliers, if any.
  ~ Dissemination of any information or reproduction of any material contained
  ~ herein is strictly forbidden, unless permitted by WSO2 in accordance with
  ~ the WSO2 Software License available at https://wso2.com/licenses/eula/3.1. For specific
  ~ language governing the permissions and limitations under this license,
  ~ please see the license as well as any agreement you’ve entered into with
  ~ WSO2 governing the purchase of this software and any associated services.
  ~
  -->
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
