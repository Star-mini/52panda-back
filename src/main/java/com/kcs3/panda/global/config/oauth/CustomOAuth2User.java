package com.kcs3.panda.global.config.oauth;

import com.kcs3.panda.domain.user.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "유저";
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return userDTO.getUserNickname();
    }

    public String getEmail(){
        return userDTO.getEmail();
    }

    public Long getUserId(){ return userDTO.getUserId();}
}
