package com.wso2.openbanking.fdx.identity.testutils;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Data Provider for FDX Identity Tests.
 */
public class IdentityTestDataProvider {

    @DataProvider(name = "nullAndEmpty")
    public Object[][] nullAndEmptyTestDataProvider() {
        return new Object[][] {
                { null },
                { "" }
        };
    }

    @DataProvider(name = "nullAndEmptyArray")
    public Object[][] nullAndEmptyArrayTestDataProvider() {
        return new Object[][] {
                { null },
                { Collections.emptyList() }
        };
    }

    @DataProvider(name = "grantTypes")
    public Object[][] grantTypesTestDataProvider() {
        List<String> defaultGrantTypes = Arrays.asList("authorization_code", "refresh_token");
        List<String> authCodeGrantType = Collections.singletonList("authorization_code");
        List<String> refreshTokenGrantType = Collections.singletonList("refresh_token");
        return new Object[][] {
                { null, defaultGrantTypes},
                {Collections.emptyList(), defaultGrantTypes},
                { Arrays.asList("authorization_code", "refresh_token"), defaultGrantTypes },
                {Collections.singletonList("authorization_code"), authCodeGrantType},
                {Collections.singletonList("refresh_token"), refreshTokenGrantType},
                {Collections.singletonList("client_credentials"), defaultGrantTypes}
        };
    }

    @DataProvider(name = "tokenEndpointAuthMethods")
    public Object[][] tokenEndpointAuthMethodsTestDataProvider() {
        String privateKeyJwt = "private_key_jwt";
        String tlsClientAUth = "tls_client_auth";
        return new Object[][] {
                { null, privateKeyJwt},
                {"", privateKeyJwt},
                {"private_key_jwt", privateKeyJwt},
                {"tls_client_auth", tlsClientAUth},
                {"sample_auth_method", privateKeyJwt}
        };
    }

}
