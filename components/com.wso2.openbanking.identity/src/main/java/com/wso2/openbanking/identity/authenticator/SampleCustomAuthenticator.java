package com.wso2.openbanking.identity.authenticator;

import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.LocalApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sample custom Authenticator implementation
 */
public class SampleCustomAuthenticator extends AbstractApplicationAuthenticator implements LocalApplicationAuthenticator {


    @Override
    protected void processAuthenticationResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationContext authenticationContext) throws AuthenticationFailedException {

    }

    @Override
    public boolean canHandle(HttpServletRequest httpServletRequest) {
        return false;
    }

    @Override
    public String getContextIdentifier(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getFriendlyName() {
        return null;
    }
}
