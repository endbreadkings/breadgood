package com.bside.breadgood.authentication.oauth2.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    //Map<String, Object> profile = (Map<String, Object>) response.get("profile");
    // .name((String)profile.get("nickname"))
    // .Picture((String)profile.get("profile_image_url"))
    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {

        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

        return (String) response.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("profile_image");
    }

}
