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

package com.wso2.openbanking.fdx.identity.dcr.model;

import com.google.gson.annotations.SerializedName;
import com.wso2.openbanking.accelerator.identity.dcr.validation.DCRCommonConstants;
import com.wso2.openbanking.accelerator.identity.dcr.validation.validationgroups.MandatoryChecks;

import java.util.Objects;
import javax.validation.constraints.NotBlank;


/**
 * Model class for FDX registry reference attribute.
 */
public class RegistryReference {

    @SerializedName("registered_entity_name")
    private String registeredEntityName;

    @SerializedName("registered_entity_id")
    private String registeredEntityId;

    @SerializedName("registry")
    private String registry;


    @NotBlank(message = "Registered Entity Name can not be null or empty in Registry References:" +
            DCRCommonConstants.INVALID_META_DATA,
            groups = MandatoryChecks.class)
    public String getRegisteredEntityName() {
        return registeredEntityName;
    }

    public void setRegisteredEntityName(String registeredEntityName) {
        this.registeredEntityName = registeredEntityName;
    }


    @NotBlank(message = "Registered Entity Id can not be null or empty in Registry References:" +
            DCRCommonConstants.INVALID_META_DATA,
            groups = MandatoryChecks.class)
    public String getRegisteredEntityId() {
        return registeredEntityId;
    }

    public void setRegisteredEntityId(String registeredEntityId) {
        this.registeredEntityId = registeredEntityId;
    }


    @NotBlank(message = "Registry can not be null or empty in Registry References:" +
            DCRCommonConstants.INVALID_META_DATA,
            groups = MandatoryChecks.class)
    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RegistryReference registryReference = (RegistryReference) obj;
        //Check if the two registry references have the same values for registeredEntityName,
        // registeredEntityId, and registry
        return Objects.equals(registeredEntityName, registryReference.registeredEntityName) &&
                Objects.equals(registeredEntityId, registryReference.registeredEntityId) &&
                Objects.equals(registry, registryReference.registry);
    }
}

