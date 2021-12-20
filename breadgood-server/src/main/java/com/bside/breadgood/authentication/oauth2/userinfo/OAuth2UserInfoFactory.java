package com.bside.breadgood.authentication.oauth2.userinfo;

import com.bside.breadgood.authentication.exception.OAuth2UserNotSupportException;
import com.bside.breadgood.ddd.users.domain.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
            return new KakaoOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            return new NaverOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.apple.toString())) {
            return new AppleOAuth2UserInfo(attributes);
        } else {
//            throw new OAuth2AuthenticationProcessingException(registrationId + " 로그인은 지원하지 않습니다.");
            throw new OAuth2UserNotSupportException(registrationId);
        }
    }
}
