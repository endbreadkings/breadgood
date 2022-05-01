package com.bside.breadgood.config;

import com.bside.breadgood.authentication.AuthEntryPoint;
import com.bside.breadgood.authentication.FilterChainExceptionHandler;
import com.bside.breadgood.ddd.users.domain.AuthProvider;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.authentication.CustomUserDetailsService;
import com.bside.breadgood.authentication.TokenAuthenticationFilter;
import com.bside.breadgood.authentication.oauth2.CustomOAuth2UserService;
import com.bside.breadgood.authentication.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bside.breadgood.authentication.oauth2.OAuth2AuthenticationFailureHandler;
import com.bside.breadgood.authentication.oauth2.OAuth2AuthenticationSuccessHandler;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final AuthEntryPoint unauthorizedHandler;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    private final UserRepository userRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.

      HttpCookieOAuth2AuthorizationReqeustRepository
      - JWT를 사용하기 때문에 Session에 저장할 필요가 없어져, Authorization Request를 Based64 encoded cookie에 저장
    */

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     /*
    아래 오류 해결 위한 bean
    AuthController required a bean of type 'org.springframework.security.crypto.password.PasswordEncoder' that could not be found.
    */

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    아래 오류 해결 위한 bean
    Parameter 0 of constructor in com.bside.breadgood.ddd.user.ui.AuthController required a bean of type 'org.springframework.security.authentication.AuthenticationManager' that could not be found.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/", "/favicon.ico", "/pages/**",
                        "/css/**", "/img/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/csrf").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/v1/s3/test/**").permitAll()
                .antMatchers("/token/refresh").permitAll()
                .antMatchers("/api/v1/user/signin", "/api/v1/admin/signin").permitAll()
//                .antMatchers("/api/v1/termsType/**").permitAll()
//                .antMatchers("/api/v1/breadstyle/**").permitAll()
                .antMatchers("/api/v1/user/social/signup").hasRole(Role.GUEST.name())
                .antMatchers("/user").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and() //anyRequest 나머지 url들을 나타냄, authenticated 인증된 경우만 허용
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                // 클라이언트 처음 로그인 시도 URI
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//                .and()
//                    .redirectionEndpoint()
//                        .baseUri("/login/oauth2/code/**")
                .and()
                .userInfoEndpoint()
                .oidcUserService(this.oidcUserService())
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);
        http
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class);
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);
            final Map<String, Object> attributes = oidcUser.getAttributes();

            final String email = (String) attributes.get("email");
            final String sub = (String) attributes.get("sub");
            final String iss = ((URL) attributes.get("iss")).getHost();
            System.out.println("iss:: " + iss);
            System.out.println("((URL) attributes.get(\"iss\"))" + attributes.get("iss").toString());

            if (!iss.contains("apple")) {
                throw new IllegalArgumentException("애플로그인만 가능합니다.");
            }

            final Optional<User> userOptional = userRepository.findBySocialLink(AuthProvider.apple, sub);
            User user;

            final OidcUserInfo userInfo = oidcUser.getUserInfo();

            if (userOptional.isPresent()) {
                user = userOptional.get();
                Set<GrantedAuthority> mappedAuthorities = Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), userInfo);
            }

            userRepository.save(User.builder()
                    .email(email)
                    .provider(AuthProvider.apple)
                    .providerId(sub)
                    .role(Role.GUEST)
                    .build());

            Set<GrantedAuthority> mappedAuthorities = Set.of(new SimpleGrantedAuthority(Role.GUEST.getKey()));
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), userInfo);

            return oidcUser;
        };

    }

    @Bean
    FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ForwardedHeaderFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}