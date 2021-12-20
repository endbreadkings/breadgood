package com.bside.breadgood.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AuthRedirectUrisProperties {

    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }

}
