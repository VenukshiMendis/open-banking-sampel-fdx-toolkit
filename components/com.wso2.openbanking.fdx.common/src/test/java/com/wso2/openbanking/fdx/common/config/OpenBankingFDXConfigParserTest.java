package com.wso2.openbanking.fdx.common.config;

import com.wso2.openbanking.accelerator.common.exception.OpenBankingRuntimeException;
import com.wso2.openbanking.fdx.common.testutils.CommonTestDataProvider;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Test Class for FDX Config Parser.
 */
public class OpenBankingFDXConfigParserTest {

    private String absolutePathForTestResources;

    @BeforeClass
    public void beforeClass() throws ReflectiveOperationException {

        //set carbon.home to current directory
        System.setProperty("carbon.home", ".");

        //get absolute path of resources directory
        String path = "src/test/resources";
        File file = new File(path);
        absolutePathForTestResources = file.getAbsolutePath();
    }

    //Runtime exception is thrown here because carbon home is not defined properly for an actual carbon product
    @Test(expectedExceptions = OpenBankingRuntimeException.class, priority = 1)
    public void testConfigParserInitiationWithoutPath() {

        OpenBankingFDXConfigParser.getInstance();

    }

    //Runtime exception is thrown here because the xml file is empty
    @Test(expectedExceptions = OpenBankingRuntimeException.class, priority = 2)
    public void testRuntimeExceptionInvalidConfigFile() {

        String path = absolutePathForTestResources + "/open-banking-fdx-empty.xml";
        OpenBankingFDXConfigParser.getInstance(path);
    }

    @Test(expectedExceptions = OpenBankingRuntimeException.class, priority = 3)
    public void testRuntimeExceptionNonExistentFile() {

        String path = absolutePathForTestResources + "/open-banking-sample.xml";
        OpenBankingFDXConfigParser.getInstance(path);

    }

    @Test(priority = 4, dataProvider = "dcrConfigs", dataProviderClass = CommonTestDataProvider.class)
    public void testConfigParserInit(String configName, String configValue) {

        String dummyConfigFile = absolutePathForTestResources + "/open-banking-fdx.xml";

        OpenBankingFDXConfigParser openBankingFDXConfigParser = OpenBankingFDXConfigParser.getInstance(dummyConfigFile);
        Map<String, Object> dcrConfigs = openBankingFDXConfigParser.getConfiguration();

        Assert.assertEquals(dcrConfigs.get(configName), configValue);

    }

    @Test(priority = 5, dataProvider = "sampleArrayConfig", dataProviderClass = CommonTestDataProvider.class)
    public void testReadChildElementArrays(String configName, List<String> configValue) {

        String dummyConfigFile = absolutePathForTestResources + "/open-banking-fdx.xml";

        OpenBankingFDXConfigParser openBankingFDXConfigParser = OpenBankingFDXConfigParser.getInstance(dummyConfigFile);
        Map<String, Object> dcrConfigs = openBankingFDXConfigParser.getConfiguration();

        Assert.assertEquals(dcrConfigs.get(configName), configValue);
    }

    @Test(priority = 6)
    public void testReplaceSystemProperty() {

        String dummyConfigFile = absolutePathForTestResources + "/open-banking-fdx.xml";

        OpenBankingFDXConfigParser openBankingFDXConfigParser = OpenBankingFDXConfigParser.getInstance(dummyConfigFile);
        Map<String, Object> dcrConfigs = openBankingFDXConfigParser.getConfiguration();

        String carbonHome = (String) dcrConfigs.get("CarbonHome");
        if (carbonHome.endsWith("\\.\\.")) {
            carbonHome = carbonHome.substring(0, carbonHome.length() - 2);
        }
        File file = new File(".");

        Assert.assertEquals(carbonHome, file.getAbsolutePath());

    }
    @Test(priority = 7)
    public void testSingleton() {

        String dummyConfigFile = absolutePathForTestResources + "/open-banking-fdx.xml";

        OpenBankingFDXConfigParser instance1 = OpenBankingFDXConfigParser.getInstance(dummyConfigFile);
        OpenBankingFDXConfigParser instance2 = OpenBankingFDXConfigParser.getInstance(dummyConfigFile);
        Assert.assertEquals(instance2, instance1);
    }

}
