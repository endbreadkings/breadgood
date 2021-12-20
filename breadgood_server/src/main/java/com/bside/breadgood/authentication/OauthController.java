//package com.bside.breadgood.authentication;
//
//import com.bside.breadgood.authentication.exception.Oauth2NotProvideRedirectUriException;
//import com.bside.breadgood.authentication.oauth2.dto.Oauth2UserSingUpRequestDto;
//import com.bside.breadgood.ddd.users.application.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/oauth2")
//@RequiredArgsConstructor
//public class OauthController {
//
//    @GetMapping("/notProvideRedirectUri")
//    public void oauth2NotProvideRedirectUri(String uri) {
//        throw new Oauth2NotProvideRedirectUriException(uri);
//    }
//
//}
