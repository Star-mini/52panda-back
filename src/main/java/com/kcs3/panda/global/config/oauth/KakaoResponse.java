package com.kcs3.panda.global.config.oauth;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> properties;

    private final Map<String, Object> account;

    private final String providerId;

    public KakaoResponse(Map<String, Object> attributes) {
        this.properties = (Map<String, Object>) attributes.get("properties");
        this.account = (Map<String, Object>) attributes.get("kakao_account");
        this.providerId = attributes.get("id").toString();
    }
    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return this.account.get("email").toString();
    }

    @Override
    public String getName() {
        return this.properties.get("nickname").toString();
    }
}
