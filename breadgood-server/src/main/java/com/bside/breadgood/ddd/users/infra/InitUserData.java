package com.bside.breadgood.ddd.users.infra;

import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.domain.UserTerms;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitUserData {

    List<User> data = new ArrayList<>();

    {

        Role role = Role.USER;
        List<UserTerms> userTermsList = new ArrayList<>();

        userTermsList.add(UserTerms.builder()
                .termsType(1L)
                .termsDate(LocalDateTime.now())
                .termsAgree(true)
                .build());

        userTermsList.add(UserTerms.builder()
                .termsType(2L)
                .termsDate(LocalDateTime.now())
                .termsAgree(true)
                .build());

        userTermsList.add(UserTerms.builder()
                .termsType(3L)
                .termsDate(LocalDateTime.now())
                .termsAgree(true)
                .build());

        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String password = bCryptPasswordEncoder.encode("1234");

        add("테스트유저", "test@breadgood.com", password, 1L, userTermsList, role);
        add("테스트유저2", "test2@breadgood.com", password, 2L, userTermsList, role);
        add("테스트유저3", "test3@breadgood.com", password, 3L, userTermsList, role);
        add("테스트유저4", "test4@breadgood.com", password, 3L, userTermsList, role);
        add("테스트유저5", "test5@breadgood.com", password, 4L, userTermsList, role);
        add("테스트유저6", "test6@breadgood.com", password, 1L, userTermsList, role);

    }

    public List<User> get() {
        return data;
    }

    private void add(String nickName, String email, String password, Long breadStyle, List<UserTerms> userTerms, Role role) {
        data.add(new User(nickName, email, password, breadStyle, userTerms, role));
    }

}
