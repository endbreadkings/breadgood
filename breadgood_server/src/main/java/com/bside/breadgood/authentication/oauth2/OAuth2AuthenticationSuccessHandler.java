package com.bside.breadgood.authentication.oauth2;

import com.bside.breadgood.authentication.AuthRedirectUrisProperties;
import com.bside.breadgood.authentication.exception.Oauth2NotProvideRedirectUriException;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.jwt.application.AccessTokenService;
import com.bside.breadgood.jwt.application.RefreshTokenService;
import com.bside.breadgood.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.bside.breadgood.authentication.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final AuthRedirectUrisProperties authRedirectUrisProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되었습니다. " + targetUrl + "로 리다이렉션을 할 수 없습니다");
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

//        System.out.println("redirectUri :: " + redirectUri.get());
//        System.out.println("isAuthorizedRedirectUri(redirectUri.get()) :: " + isAuthorizedRedirectUri(redirectUri.get()));
//        System.out.println("redirectUri.isPresent() :: " + redirectUri.isPresent());
        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new IllegalArgumentException("승인되지 않은 리디렉션 URI가 있어 인증을 진행할 수 없습니다.");
            return "/oauth2/notProvideRedirectUri?uri=" + redirectUri.get();
        }

        final String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        final String accessToken = accessTokenService.createTokenByAuthentication(authentication);
        System.out.println("accessToken :: " + accessToken);
        final Long accessTokenExpirationTimeMsec = accessTokenService.getExpirationTime(accessToken);


        final String refreshToken = refreshTokenService.createTokenByAuthentication(authentication);
        final Long refreshTokenExpirationTimeMsec = refreshTokenService.getExpirationTime(refreshToken);

        final Boolean isGuest = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(Role.GUEST.getKey()));


        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("accessTokenExpirationTimeMsec", accessTokenExpirationTimeMsec)
                .queryParam("refreshToken", refreshToken)
                .queryParam("refreshTokenExpirationTimeMsec", refreshTokenExpirationTimeMsec)
                .queryParam("tokenType", "Bearer")
                .queryParam("isGuest", isGuest)
                .build().toUriString();

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        return authRedirectUrisProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> authorizedRedirectUri.equals(uri));
    }
}