/**
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wso2.openbanking.fdx.identity.dcr.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum for FDX supported token endpoint auth methods.
 */
public enum AllowedTokenEndPointAuthMethodsEnum {
    PRIVATE_KEY_JWT("private_key_jwt"),
    TLS_CLIENT_AUTH("tls_client_auth");

    private final String value;

    public String getValue() {
        return value;
    }

    AllowedTokenEndPointAuthMethodsEnum(String value) {
        this.value = value;
    }

    public static List<String> getAllowedAuthMethods() {
        List<String> allowedTokenEndPointAuthMethods = Arrays.stream(AllowedTokenEndPointAuthMethodsEnum.values())
                .map(AllowedTokenEndPointAuthMethodsEnum::getValue)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(allowedTokenEndPointAuthMethods);
    }
}
