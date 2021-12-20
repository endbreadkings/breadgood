package com.bside.breadgood.jwt.application;

import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.jwt.infra.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TokenDeleteService {

    private static final Logger logger = LoggerFactory.getLogger(TokenDeleteService.class);

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Transactional
    public void deleteByUserId(Long userId) {
//        User user = userService.findById(userId);
//        return refreshTokenRepository.deleteByUser(user);
    }

}
