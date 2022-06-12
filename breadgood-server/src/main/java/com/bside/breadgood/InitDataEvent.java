package com.bside.breadgood;

import com.bside.breadgood.ddd.bakery.application.BakeryService;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.users.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!test")
public class InitDataEvent {

    private final TermsTypeService termsTypeService;
    private final BreadStyleService breadStyleService;
    private final BakeryCategoryService bakeryCategoryService;
    private final EmojiService emojiService;
    private final UserService userService;
    private final BakeryService bakeryService;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("[ApplicationReadyEvent] 이벤트: 데이터 셋팅 메소드 실행");

        // 약간 더미 데이터 생성 -> 초기 데이터
        termsTypeService.initData();

        // 빵 스타일 더미 데이터 생성 -> 초기 데이터
        breadStyleService.initData();

        // 사용자 더미 데이터 생성 -> 초기 데이터
        userService.initData();

        // 빵집 카테고리 더미 데이터 생성 -> 초기 데이터
        bakeryCategoryService.initData();

        // 이모지 더미 데이터 생성 -> 초기 데이터
        emojiService.initData();

        // 빵집 더미 데이터 생성
        bakeryService.initData();

        // 테스트 3번 사용자 탈퇴 처리.
        userService.withdrawal(3L);
    }
}

