/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Software License available at https://wso2.com/licenses/eula/3.1.
 * For specific language governing the permissions and limitations under this
 * license, please see the license as well as any agreement you’ve entered into
 * with WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.openbanking.identity.claims;

import com.wso2.openbanking.accelerator.identity.claims.OBClaimProvider;
import com.wso2.openbanking.accelerator.identity.claims.OBDefaultClaimProvider;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AuthorizeRespDTO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;

import java.util.Map;

/**
 * Sample claim provider implementation
 */
public class OBSampleClaimProvider extends OBClaimProvider {

    OBDefaultClaimProvider obDefaultClaimProvider = new OBDefaultClaimProvider();

    @Override
    public Map<String, Object> getAdditionalClaims(OAuthAuthzReqMessageContext authAuthzReqMessageContext,
                                                   OAuth2AuthorizeRespDTO authorizeRespDTO)
            throws IdentityOAuth2Exception {

        return obDefaultClaimProvider.getAdditionalClaims(authAuthzReqMessageContext, authorizeRespDTO);

    }

    @Override
    public Map<String, Object> getAdditionalClaims(OAuthTokenReqMessageContext tokenReqMessageContext,
                                                   OAuth2AccessTokenRespDTO tokenRespDTO)
            throws IdentityOAuth2Exception {

        return obDefaultClaimProvider.getAdditionalClaims(tokenReqMessageContext, tokenRespDTO);
    }
}
