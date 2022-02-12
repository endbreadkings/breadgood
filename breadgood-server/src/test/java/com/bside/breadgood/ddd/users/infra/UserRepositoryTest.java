package com.bside.breadgood.ddd.users.infra;

import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/02/12
 * description :
 */
@DataJpaTest
@DisplayName("사용자 리파지토리 테스트")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(
                new User(
                        "세균맨",
                        "virus0@xmail.com",
                        "8282",
                        null,
                        "profile.png",
                        null,
                        Role.USER
                )
        );
    }

    @Test
    @DisplayName("사용자 등록을 테스트한다")
    public void createUser() {
        // given
        final User user = new User(
                "호빵맨",
                "ho0@xmail.com",
                "0000",
                null,
                "sample.png",
                null,
                Role.USER
        );

        // when
        final User actual = userRepository.save(user);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("사용자 목록 조회를 테스트한다")
    public void readUsers() {
        // when
        final List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("사용자 닉네임을 업데이트한다")
    public void updateNickName() {
        // given
        User user = userRepository.findById(savedUser.getId())
                .orElseThrow(NullPointerException::new);
        // when
        user.updateNickName("세균맨2");

        final User actual = userRepository.findByEmail("virus0@xmail.com")
                .orElseThrow(NullPointerException::new);

        // then
        assertThat(actual.getNickName()).isEqualTo("세균맨2");
    }

    @Test
    @DisplayName("사용자를 삭제한다")
    public void deleteUser() {
        // when
        userRepository.delete(savedUser);
        final List<User> users = userRepository.findAll();

        // then
        assertThat(users).hasSize(0);
    }


}