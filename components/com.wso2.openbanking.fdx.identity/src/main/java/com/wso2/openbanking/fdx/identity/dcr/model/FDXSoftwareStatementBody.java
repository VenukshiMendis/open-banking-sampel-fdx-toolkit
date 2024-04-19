package com.wso2.openbanking.fdx.identity.dcr.model;

import com.google.gson.annotations.SerializedName;
import com.wso2.openbanking.accelerator.identity.dcr.model.SoftwareStatementBody;

import java.util.List;

/**
 * Model class for FDX dcr software statement body.
 */
public class FDXSoftwareStatementBody extends SoftwareStatementBody {

    @SerializedName("description")
    private String description;

    @SerializedName("logo_uri")
    private String logoUri;

    @SerializedName("client_uri")
    private String clientUri;

    @SerializedName("contacts")
    private List<String> contacts;

    @SerializedName("duration_type")
    private List<String> durationType;

    @SerializedName("duration_period")
    private Integer durationPeriod;

    @SerializedName("lookback_period")
    private Integer lookbackPeriod;

    @SerializedName("registry_references")
    private List<RegistryReference> registryReferences;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getDurationType() {
        return durationType;
    }

    public void setDurationType(List<String> durationType) {
        this.durationType = durationType;
    }

    public Integer getDurationPeriod() {
        return durationPeriod;
    }

    public void setDurationPeriod(Integer durationPeriod) {
        this.durationPeriod = durationPeriod;
    }

    public Integer getLookbackPeriod() {
        return lookbackPeriod;
    }

    public void setLookbackPeriod(Integer lookbackPeriod) {
        this.lookbackPeriod = lookbackPeriod;
    }

    public List<RegistryReference> getRegistryReferences() {
        return registryReferences;
    }

    public void setRegistryReferences(List<RegistryReference> registryReferences) {
        this.registryReferences = registryReferences;
    }
}
