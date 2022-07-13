package com.bside.breadgood.ddd.users.application;

import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.*;
import static com.bside.breadgood.fixtures.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;

/**
 * author : haedoang
 * date : 2022/06/09
 * description :
 */
@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BreadStyleService breadStyleService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 id 리스트로 조회한다")
    public void findAllById() {
        // given
        Map<Long, BreadStyleResponseDto> breadStyleMap = new HashMap<>();
        breadStyleMap.put(1L, new BreadStyleResponseDto(크림_100));
        breadStyleMap.put(2L, new BreadStyleResponseDto(달콤_200));
        breadStyleMap.put(3L, new BreadStyleResponseDto(짭짤_300));
        given(userRepository.findAllById(anySet())).willReturn(Lists.newArrayList(테스트유저, 테스트유저2, 테스트유저3));
        given(breadStyleService.findAllById(anySet())).willReturn(breadStyleMap);

        // when
        final Map<Long, UserInfoResponseDto> actual = userService.findAllById(Sets.newHashSet(1L, 2L, 3L));

        // then
        assertThat(actual).hasSize(3);
        assertThat(actual.values()).extracting(UserInfoResponseDto::getId).contains(1L, 2L, 3L);
        assertThat(actual.values()).extracting(UserInfoResponseDto::getNickName).contains("테스트유저", "테스트유저2", "테스트유저3");
    }
}
