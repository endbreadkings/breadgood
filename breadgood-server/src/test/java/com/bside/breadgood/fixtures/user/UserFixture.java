package com.bside.breadgood.fixtures.user;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.users.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author : haedoang
 * date : 2022/02/16
 * description :
 */
public class UserFixture {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 암호화되지 않은 평문 비밀번호를 입력받습니다
     */
    public static User 사용자_등록_요청(
            String nickName,
            String email,
            String notEncryptedPassword,
            List<TermsType> termsTypes,
            Long breadStyledId,
            Role role
    ) {
        final List<UserTerms> userTerms = termsTypes.stream()
                .map(it ->
                        UserTerms.builder()
                                .termsType(it.getId())
                                .termsAgree(true)
                                .termsDate(LocalDateTime.now())
                                .build()
                )
                .collect(Collectors.toList());

        return new User(
                nickName,
                email,
                encoder.encode(notEncryptedPassword),
                breadStyledId,
                userTerms,
                role
        );
    }

    public static final User 테스트유저 =
            new User(
                    1L,
                    NickName.valueOf("테스트유저"),
                    Email.valueOf("test@breadgood.com"),
                    "1234",
                    1L,
                    null,
                    null,
                    Role.USER
            );

    public static final User 관리자 =
            new User(
                    "관리자",
                    "admin@breadgood.com",
                    "1234",
                    1L,
                    null,
                    Role.ADMIN
            );
}
