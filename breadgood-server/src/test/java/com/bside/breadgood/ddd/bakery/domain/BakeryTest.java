package com.bside.breadgood.ddd.bakery.domain;

import com.bside.breadgood.ddd.bakery.infra.BakeryRepository;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.빵에집중;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지_100;
import static com.bside.breadgood.fixtures.user.UserFixture.테스트유저;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("빵집 엔티티 생성 테스트")
class BakeryTest {

    @Autowired
    private BakeryRepository bakeryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BakeryCategoryService bakeryCategoryService;
    @Autowired
    private EmojiService emojiService;
    @Autowired
    private BreadStyleService breadStyleService;

    User user;
    Emoji emoji;
    BreadStyle breadStyle;
    BakeryCategory bakeryCategory;

    @BeforeEach
    void setUp() {
        user = userRepository.save(테스트유저);
        emoji = emojiRepository.save(이모지_100);
        breadStyle = breadStyleRepository.save(달콤_200);
        bakeryCategory = bakeryCategoryRepository.save(빵에집중);
    }

    @Test
    void 빵집_엔티티_생성() {

        final long emojiId = 1L;
        final long bakeryStyleId = 1L;
        final long userId = 1L;
        final long bakeryCategoryId = 2L;

        final String fileHost = "https://d74hbwjus7qtu.cloudfront.net/";
        final List<String> filePaths = Collections.singletonList("admin/address-icon.png");
        final UserResponseDto userResponseDto = userService.findById(userId);
        final BakeryCategoryResponseDto bakeryCategoryResponseDto2 = bakeryCategoryService.findById(bakeryCategoryId);
        final EmojiResponseDto emojiResponseDto = emojiService.findById(emojiId);
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(bakeryStyleId);

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
            assertEquals(fetchedBakery.getBakeryCategory(), bakeryCategoryId, "빵집 카테고리 정보가 일치하지 않습니다.");
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
            assertNotNull(fetchedBakery.getCreated_at(), "빵집 등록 일자가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList(), "빵집 리뷰 정보 리스트가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0), "빵집 리뷰 정보가 존재하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0).getImgUrls(), "빵집 리뷰 정보 중 인증사진 정보가 존재하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getImgUrls().get(0), fileHost+ filePaths.get(0), "인증사진 주소가 일치하지 않습니다.");

            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getBreadStyle().getBreadStyleName(), breadStyleResponseDto.getName(), "최애빵 스타일 이름이 일치하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getBreadStyle().getBreadStyleImgUrl(), breadStyleResponseDto.getImgUrl(), "최애빵 스타일 이미지 주소가 일치하지 않습니다.");

            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getImgHost(), fileHost, "최애빵 스타일 이미지 주소 호스트가 일치하지 않습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getEmoji(), emojiId, "이모지 내용이 일치하지 않습니다.");
            assertNotNull(fetchedBakery.getBakeryReviewList().get(0).getId(), "빵집 리뷰 아이디가 없습니다.");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getUser(), userId, "");
            assertEquals(fetchedBakery.getBakeryReviewList().get(0).getContent(), content, "리뷰 작성 내용이 일치하지 않습니다.");

            final List<String> fetchedSignatureMenus = fetchedBakery.getBakeryReviewList().get(0).getSignatureMenus();
            assertTrue(fetchedSignatureMenus.size() == signatureMenus.size()
                    && fetchedSignatureMenus.containsAll(signatureMenus) && signatureMenus.containsAll(fetchedSignatureMenus));

        });

    }
}
