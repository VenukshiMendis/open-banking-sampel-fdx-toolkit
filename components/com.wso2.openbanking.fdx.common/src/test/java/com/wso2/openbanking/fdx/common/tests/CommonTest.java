package com.wso2.openbanking.fdx.common.tests;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Data Provider for FDX Common Tests.
 */
public class CommonTest {

    @Test
    public void sampleTest() {
        String str = "jh";
        Assert.assertEquals(str, "jh");
    }
}
