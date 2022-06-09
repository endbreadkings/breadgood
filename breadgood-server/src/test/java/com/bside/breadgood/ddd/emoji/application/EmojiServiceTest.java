package com.bside.breadgood.ddd.emoji.application;

import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_100;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_등록요청_생성;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_요청이미지;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.bside.breadgood.ddd.emoji.application.dto.EmojiRequestDto;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.s3.application.S3Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * create on 2022/06/08. create by IntelliJ IDEA.
 *
 * <p> 이모지 서비스 테스트 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 */
@DisplayName("이모지 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class EmojiServiceTest {
  @Mock
  private EmojiRepository emojiRepository;

  @Mock
  private S3Service s3Service;

  @InjectMocks
  private EmojiService emojiService;

  @Test
  @DisplayName("이모지 저장")
  void save() {
    // given
    given(s3Service.upload(이모지_요청이미지, "admin"))
        .willReturn("admin/" + 이모지_요청이미지.getName());
    given(s3Service.getFileHost()).willReturn("https://d74hbwjus7qtu.cloudfront.net/");
    given(emojiRepository.save(any())).willReturn(이모지_100);
    given(emojiRepository.findMaxSortNumber()).willReturn(0);
    EmojiRequestDto 이모지_등록요청 = 이모지_등록요청_생성("별로에요");

    // when
    EmojiResponseDto actual = emojiService.save(이모지_등록요청, 이모지_요청이미지);

    // then
    assertThat(actual).isEqualTo(new EmojiResponseDto(이모지_100));
  }
}