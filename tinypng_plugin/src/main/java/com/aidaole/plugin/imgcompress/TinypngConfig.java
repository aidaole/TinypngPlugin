package com.aidaole.plugin.imgcompress;

public class TinypngConfig {
    public String apiKey = "";
    public String imgTypes = "";

    public TinypngConfig() {
    }

    public TinypngConfig(String apiKey, String imgTypes) {
        this.apiKey = apiKey;
        this.imgTypes = imgTypes;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getImgTypes() {
        return imgTypes;
    }

    public void setImgTypes(String imgTypes) {
        this.imgTypes = imgTypes;
    }
}
