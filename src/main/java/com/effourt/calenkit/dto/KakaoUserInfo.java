package com.effourt.calenkit.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUserInfo {

    private Map<String, Object> attributes;
    private Map<String, Object> accountAttributes;
    private Map<String, Object> profileAttributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.accountAttributes = (Map<String, Object>) attributes.get("kakao_account");
        this.profileAttributes = (Map<String, Object>) accountAttributes.get("profile");
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getEmail() {
        return accountAttributes.get("email").toString();
    }

    public String getNickname() {
        return profileAttributes.get("nickname").toString();
    }

    public String getProfileImage() {
        return profileAttributes.get("profile_image_url").toString();
    }
}
