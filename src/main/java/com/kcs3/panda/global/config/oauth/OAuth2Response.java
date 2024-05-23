package com.kcs3.panda.global.config.oauth;

public interface OAuth2Response {
    String getProvider();
    String getEmail();

    String getName();
}
