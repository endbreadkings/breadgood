package com.bside.breadgood.authentication.oauth2;

import com.bside.breadgood.authentication.UserPrincipal;
import com.bside.breadgood.authentication.exception.OAuth2AlreadyRegisterException;
import com.bside.breadgood.authentication.exception.OAuth2NotFoundEmailException;
import com.bside.breadgood.authentication.oauth2.userinfo.OAuth2UserInfo;
import com.bside.breadgood.authentication.oauth2.userinfo.OAuth2UserInfoFactory;
import com.bside.breadgood.ddd.users.domain.AuthProvider;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {

            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    // ????????? ?????? ??????
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
//            throw new OAuth2AuthenticationProcessingException("OAuth2 ?????????(?????????) ?????? ???????????? ?????? ??? ????????????.");
            throw new OAuth2NotFoundEmailException(registrationId);
        }


        Optional<User> userOptional = userRepository.findBySocialLink(AuthProvider.valueOf(registrationId), oAuth2UserInfo.getId());
        User user;

        // ?????? ?????????
        if (userOptional.isPresent()) {

            user = userOptional.get();

            // ????????? ?????? ???????????? ?????? ???????????? ???????????? ?????? ?????????
            AuthProvider userProvider = user.getSocialLink().getProvider();
            if (!userProvider.equals(AuthProvider.valueOf(registrationId))) {
//                throw new OAuth2AuthenticationProcessingException(
//                        userProvider.name() + " ?????? ???????????? ???????????? ?????? ?????????????????????.");
                throw new OAuth2AlreadyRegisterException(userProvider.name());
            }

            return UserPrincipal.create(user, oAuth2User.getAttributes());
        }


        // ???????????? ???????????? ????????? ???????????? ??????
        user = registerGuest(oAuth2UserRequest, oAuth2UserInfo);
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    // DB??? ???????????? ?????? ?????? ?????? ??????
    private User registerGuest(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        log.debug("[OAuthUser] id => " + oAuth2UserInfo.getId());
        log.debug("[OAuthUser] name => " + oAuth2UserInfo.getName());
        log.debug("[OAuthUser] imageUrl => " + oAuth2UserInfo.getImageUrl());
        log.debug("[OAuthUser] email => " + oAuth2UserInfo.getEmail());
        log.debug("[OAuthUser] registrationId => " + AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));

        return userRepository.save(User.builder()
                .email(oAuth2UserInfo.getEmail())
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .role(Role.GUEST)
                .build());
    }


}
