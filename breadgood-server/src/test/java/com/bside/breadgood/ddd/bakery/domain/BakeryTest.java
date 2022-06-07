package com.bside.breadgood.ddd.bakery.domain;

import com.bside.breadgood.ddd.bakery.infra.BakeryRepository;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.fixtures.bakery.BakeryFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.빵에집중;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지1;
import static com.bside.breadgood.fixtures.user.UserFixture.테스트유저;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("빵집 엔티티 생성 테스트")
class BakeryTest {

    @Autowired
    private BakeryRepository bakeryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BakeryCategoryRepository bakeryCategoryRepository;

    @Autowired
    private BakeryCategoryService bakeryCategoryService;

    @Autowired
    private EmojiRepository emojiRepository;

    @Autowired
    private EmojiService emojiService;

    @Autowired
    private BreadStyleRepository breadStyleRepository;

    @Autowired
    private BreadStyleService breadStyleService;

    User user;
    Emoji emoji;
    BreadStyle breadStyle;
    BakeryCategory bakeryCategory;

    @BeforeEach
    void setUp() {
        user = userRepository.save(테스트유저);
        emoji = emojiRepository.save(이모지1);
        breadStyle = breadStyleRepository.save(달콤_200);
        bakeryCategory = bakeryCategoryRepository.save(빵에집중);
    }

    @Test
    void 빵집_엔티티_생성() {
        // given
        final String fileHost = "https://d74hbwjus7qtu.cloudfront.net/";
        final List<String> filePaths = Collections.singletonList("admin/address-icon.png");
        final UserResponseDto userResponseDto = userService.findById(user.getId());
        final BakeryCategoryResponseDto bakeryCategoryResponseDto2 = bakeryCategoryService.findById(bakeryCategory.getId());
        final EmojiResponseDto emojiResponseDto = emojiService.findById(emoji.getId());
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(breadStyle.getId());

        String city = "서울특별시";
        String content = "통밀한빵 정말 맛있어요.";
        String description = "";
        String district = "강서구";
        double mapX = 14121066.7281847;
        double mapY = 4516759.6029301;
        String roadAddress = "서울특별시 강서구 공항대로 437";
        List<String> signatureMenus = Arrays.asList("통밀빵", "한빵");
        String title = "통밀한빵";

        final Bakery bakery = Bakery.builder()
                .bakeryCategory(bakeryCategoryResponseDto2)
                .city(city)
                .content(content)
                .description(description)
                .district(district)
                .emoji(emojiResponseDto)
                .imgHost(fileHost)
                .imgUrls(filePaths)
                .mapX(mapX)
                .mapY(mapY)
                .roadAddress(roadAddress)
                .signatureMenus(signatureMenus)
                .title(title)
                .user(userResponseDto)
                .breadStyle(breadStyleResponseDto)
                .build();
        final Bakery fetchedBakery = bakeryRepository.save(bakery);


        assertAll(() -> {
            assertNotNull(fetchedBakery, "빵집 엔티티가 빈값입니다.");
            assertEquals(fetchedBakery.getBakeryCategory(), bakeryCategory.getId(), "빵집 카테고리 정보가 일치하지 않습니다.");
            assertNotNull(fetchedBakery.getAddress(), "빵집 주소 정보가 없습니다.");
            assertEquals(fetchedBakery.getAddress().getCity(), city, "빵집 대분류가 일치 하지 않습니다.");
            assertEquals(fetchedBakery.getAddress().getDistrict(), district, "빵집 중분류가 일치 하지 않습니다.");
            assertEquals(fetchedBakery.getAddress().getRoadAddress(), roadAddress, "빵집 도로명 주소가 일치 하지 않습니다.");
            assertNotNull(fetchedBakery.getId(), "빵집 아이디가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getPoint(), "빵집 위치 정보가 존재하지 않습니다.");
            assertEquals(fetchedBakery.getPoint().getMapX(), mapX);
            assertEquals(fetchedBakery.getPoint().getMapY(), mapY);
            assertEquals(fetchedBakery.getTitle(), title, "빵집 상호명이 일치하지 않습니다.");
            assertEquals(fetchedBakery.getDescription(), description, "상세정보가 일치 하지 않습니다.");
            assertNotNull(fetchedBakery.getUser(), "빵집 등록자 정보가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getCreatedAt(), "빵집 등록 일자가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList(), "빵집 리뷰 정보 리스트가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0), "빵집 리뷰 정보가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0).getImgUrls(), "빵집 리뷰 정보 중 인증사진 정보가 존재하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getImgUrls().get(0), fileHost + filePaths.get(0), "인증사진 주소가 일치하지 않습니다.");

            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getBreadStyle().getBreadStyleName(), breadStyleResponseDto.getName(), "최애빵 스타일 이름이 일치하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getBreadStyle().getBreadStyleImgUrl(), breadStyleResponseDto.getImgUrl(), "최애빵 스타일 이미지 주소가 일치하지 않습니다.");

            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getImgHost(), fileHost, "최애빵 스타일 이미지 주소 호스트가 일치하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getEmoji(), emoji.getId(), "이모지 내용이 일치하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0).getId(), "빵집 리뷰 아이디가 없습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getUser(), user.getId(), "");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getContent(), content, "리뷰 작성 내용이 일치하지 않습니다.");

            final List<String> fetchedSignatureMenus = fetchedBakery.getBakeryReviewList().get(0).getSignatureMenus();
            assertTrue(fetchedSignatureMenus.size() == signatureMenus.size()
                    && fetchedSignatureMenus.containsAll(signatureMenus) && signatureMenus.containsAll(fetchedSignatureMenus));
            assertFalse(fetchedBakery.isDeleted());
        });

    }

}