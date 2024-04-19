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
 * Enum for FDX supported scopes.
 */
public enum FDXScopesEnum {
    ACCOUNT_BASIC,
    ACCOUNT_DETAILED ,
    ACCOUNT_PAYMENTS,
    PAYMENT_SUPPORT,
    CUSTOMER_CONTACT,
    CUSTOMER_PERSONAL,
    INVESTMENTS,
    STATEMENTS,
    TRANSACTIONS,
    BILLS,
    IMAGES,
    REWARDS,
    TAX;


    //get a list of all FDX Scopes in string format
    public static List<String> getAllFDXScopes() {
        List<String> fdxScopes = Arrays.stream(FDXScopesEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(fdxScopes);
    }

}
