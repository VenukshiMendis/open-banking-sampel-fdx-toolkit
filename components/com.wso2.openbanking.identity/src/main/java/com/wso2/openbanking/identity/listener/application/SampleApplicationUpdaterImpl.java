/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Commercial License available at http://wso2.com/licenses. For specific
 * language governing the permissions and limitations under this license,
 * please see the license as well as any agreement you’ve entered into with
 * WSO2 governing the purchase of this software and any associated services.
 */
package com.wso2.openbanking.identity.listener.application;

import com.wso2.openbanking.accelerator.common.exception.OpenBankingException;
import com.wso2.openbanking.accelerator.identity.listener.application.ApplicationUpdaterImpl;
import org.wso2.carbon.identity.application.common.model.LocalAndOutboundAuthenticationConfig;
import org.wso2.carbon.identity.application.common.model.ServiceProvider;
import org.wso2.carbon.identity.application.common.model.ServiceProviderProperty;
import org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO;

import java.util.Map;

/**
 * Default implementation class for AbstractApplicationUpdater which should be extended for spec specific
 * tasks
 */
public class SampleApplicationUpdaterImpl extends ApplicationUpdaterImpl {

    @Override
    public void setOauthAppProperties(boolean isRegulatoryApp, OAuthConsumerAppDTO oauthApplication, Map<String,
            Object> spMetaData) throws OpenBankingException {
        super.setOauthAppProperties(isRegulatoryApp, oauthApplication, spMetaData);
    }

    @Override
    public void setServiceProviderProperties(boolean isRegulatoryApp, ServiceProvider serviceProvider,
                                             ServiceProviderProperty[] serviceProvideProperties)
            throws OpenBankingException {
        super.setServiceProviderProperties(isRegulatoryApp, serviceProvider, serviceProvideProperties);
    }

    @Override
    public void setAuthenticators(boolean isRegulatoryApp, String tenantDomain, ServiceProvider serviceProvider,
                                  LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig)
            throws OpenBankingException {
        super.setAuthenticators(isRegulatoryApp, tenantDomain, serviceProvider, localAndOutboundAuthenticationConfig);
    }

    @Override
    public void setConditionalAuthScript(boolean isRegulatoryApp, ServiceProvider serviceProvider,
                                         LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig)
            throws OpenBankingException {
        super.setConditionalAuthScript(isRegulatoryApp, serviceProvider, localAndOutboundAuthenticationConfig);
    }

    @Override
    public void publishData(Map<String, Object> spMetaData, OAuthConsumerAppDTO oAuthConsumerAppDTO)
            throws OpenBankingException {
        super.publishData(spMetaData, oAuthConsumerAppDTO);
    }

    @Override
    public void doPreCreateApplication(boolean isRegulatoryApp, ServiceProvider serviceProvider,
                                       LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig,
                                       String tenantDomain, String userName) throws OpenBankingException {
        super.doPreCreateApplication(isRegulatoryApp, serviceProvider, localAndOutboundAuthenticationConfig,
                tenantDomain, userName);
    }

    @Override
    public void doPostGetApplication(ServiceProvider serviceProvider, String applicationName, String tenantDomain)
            throws OpenBankingException {
        super.doPostGetApplication(serviceProvider, applicationName, tenantDomain);
    }

    @Override
    public void doPreUpdateApplication(boolean isRegulatoryApp, OAuthConsumerAppDTO oauthApplication,
                                       ServiceProvider serviceProvider,
                                       LocalAndOutboundAuthenticationConfig localAndOutboundAuthenticationConfig,
                                       String tenantDomain, String userName) throws OpenBankingException {
        super.doPreUpdateApplication(isRegulatoryApp, oauthApplication, serviceProvider,
                localAndOutboundAuthenticationConfig, tenantDomain, userName);
    }

    @Override
    public void doPreDeleteApplication(String applicationName, String tenantDomain, String userName)
            throws OpenBankingException {
        super.doPreDeleteApplication(applicationName, tenantDomain, userName);
    }

    @Override
    public void doPostDeleteApplication(ServiceProvider serviceProvider, String tenantDomain, String userName)
            throws OpenBankingException {
        super.doPostDeleteApplication(serviceProvider, tenantDomain, userName);
    }
}
