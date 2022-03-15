package com.bside.breadgood.ddd.bakery.application;

import com.bside.breadgood.common.vo.ImageUrl;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchResponseDto;
import com.bside.breadgood.ddd.bakery.application.exception.IllegalCityException;
import com.bside.breadgood.ddd.bakery.domain.Address;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.bakery.domain.Point;
import com.bside.breadgood.ddd.bakery.infra.BakeryRepository;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import com.bside.breadgood.ddd.users.application.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.ddd.users.domain.Email;
import com.bside.breadgood.ddd.users.domain.NickName;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.s3.application.S3Service;
import com.bside.breadgood.s3.application.dto.S3UploadResponseDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BakeryServiceTest {

    BakeryService bakeryService;

    BakeryRepository bakeryRepository;

    UserService userService;

    BakeryCategoryService bakeryCategoryService;

    S3Service s3Service;

    EmojiService emojiService;

    BreadStyleService breadStyleService;

    @BeforeEach
    void setUp() {
        bakeryRepository = mock(BakeryRepository.class);
        userService = mock(UserService.class);
        bakeryCategoryService = mock(BakeryCategoryService.class);
        s3Service = mock(S3Service.class);
        emojiService = mock(EmojiService.class);
        breadStyleService = mock(BreadStyleService.class);

        bakeryService = new BakeryService(bakeryRepository, s3Service, userService, bakeryCategoryService, emojiService, breadStyleService);
    }

    @Test
    void 서울_로_시작하는_주소인지_확인() {
        String containsWord = "서울";
        String address = "서울특별시 중구 을지로3가 229-1 ";
        String address2 = "서울특별시 중구 을지로3가 229-1 ";
        assertTrue(address.contains(containsWord));
        assertTrue(address2.contains(containsWord));
    }

    @Test
    void 특정_숫자_정렬_확인() {
        List<Long> userUid = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        Comparator<Long> comparator = (o1, o2) -> {
            if (o1 == 2) return -1;
            if (o2 == 2) return 1;
            return o2.compareTo(o1);
        };

        Collections.sort(userUid, comparator);

        System.out.println(userUid);

//        List<String> res =  userUid.stream().sorted()
//                .filter(u -> !u.equals("uid2"))
//                .collect(ArrayList::new,
//                        (l, s) ->{if (l.isEmpty())l.add("uid2");l.add(s);},
//                        List::addAll );
//
//        System.out.println(res);
    }

    @Test
    void 빵집_리스트_검색_전체검색() {
        // given
        String city = "서울특별시";
        String district = "";
        Set<Long> bakeryCategories = Set.of(1L, 2L);

        BakerySearchRequestDto request = BakerySearchRequestDto.builder()
                .city(city)
                .district(district)
                .bakeryCategories(bakeryCategories)
                .build();

        // when
        setSearchMockReturns();
        List<BakerySearchResponseDto> response = bakeryService.search(request);

        //then
        assertEquals(2, response.size());
    }

    @Test
    void 빵집_리스트_검색_구검색() {
        // given
        String city = "서울특별시";
        String district = "구로구";
        Set<Long> bakeryCategories = Set.of(1L, 2L);

        BakerySearchRequestDto request = BakerySearchRequestDto.builder()
                .city(city)
                .district(district)
                .bakeryCategories(bakeryCategories)
                .build();

        // when
        setSearchMockReturns();
        List<BakerySearchResponseDto> response = bakeryService.search(request);

        //then
        assertEquals(1, response.size());
        assertTrue(response.get(0).getRoadAddress().contains(district));
    }

    @Test
    void 빵집_리스트_검색_카테고리_검색() {
        // given
        String city = "서울특별시";
        String district = "";
        Set<Long> bakeryCategories = Set.of(1L);

        BakerySearchRequestDto request = BakerySearchRequestDto.builder()
                .city(city)
                .district(district)
                .bakeryCategories(bakeryCategories)
                .build();

        // when
        setSearchMockReturns();
        List<BakerySearchResponseDto> response = bakeryService.search(request);

        //then
        assertEquals(1, response.size());
        assertEquals("카테고리1", response.get(0).getCategoryTitle());
    }

    private List<Bakery> getDummyBakeries() {
        Address address1 = new Address("서울특별시", "강남구", "서울특별시 강남구 테헤란로");
        Address address2 = new Address("서울특별시", "구로구", "서울특별시 구로구 무슨길로");

        Point point = new Point(34.23453, 127.49342);

        Bakery bakery1 = new Bakery(1L, "1번 빵집", "1번 빵집 설명입니다.", 1L, address1, point, 1L);
        Bakery bakery2 = new Bakery(2L, "2번 빵집", "2번 빵집 설명입니다.", 1L, address2, point, 2L);

        // review
        User user = new User(1L, NickName.valueOf("테스트유저"), Email.valueOf("test@breadgood.com"), "1234", 1L, null, null, Role.USER);
        UserResponseDto userDto = new UserResponseDto(user);

        Emoji emoji = new Emoji(1L, "emojiName", "img.url", 1);
        EmojiResponseDto emojiDto = new EmojiResponseDto(emoji);

        BreadStyle breadStyle = new BreadStyle(1L, "담백", "담백빵 내용", ImageUrl.from("https://test.domain.com/path1/img.png"), ImageUrl.from("https://test.domain.com/path1/img.png"), "#FFFFFF");
        BreadStyleResponseDto breadStyleDto = new BreadStyleResponseDto(breadStyle);

        bakery1.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);
        bakery2.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);

        return List.of(bakery2, bakery1);
    }

    private void setSearchMockReturns() {
        BakeryCategory category1 = new BakeryCategory(1L,
                "카테고리1",
                "카테고리1 설명입니다.",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                "#FFFFFF",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                1);
        BakeryCategory category2 = new BakeryCategory(2L,
                "카테고리2",
                "카테고리2 설명입니다.",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                "#000000",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                2);
        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.builder()
                .nickName("테스트유저")
                .userId(1L)
                .profileImgUrl("img.img")
                .breadStyleId(1L)
                .breadStyleName("담백")
                .isWithdrawal(true)
                .build();
        List<Bakery> bakeries = getDummyBakeries();

        // when
        when(userService.findUserInfoById(1L))
                .thenReturn(userInfoResponseDto);
        when(bakeryCategoryService.findById(1L))
                .thenReturn(new BakeryCategoryResponseDto(category1));
        when(bakeryCategoryService.findById(2L))
                .thenReturn(new BakeryCategoryResponseDto(category2));
        when(bakeryRepository.findAllOrderByIdDesc())
                .thenReturn(bakeries);
    }

//    @Test
//    void findByIdAndUserId() {
//        final BakeryResponseDto bakeryResponseDto = bakeryService.findByIdAndUserId(1L, 1L);
//        System.out.println(bakeryResponseDto.getBakeryReviews());
//        assertEquals(bakeryResponseDto.getBakeryReviews().get(0).getUserId(), 1L);
//    }


    @Test
    @DisplayName("베이커리는 서울, 서울특별시만 등록 가능하다")
    public void saveBakerySomeCities() {
        // given
        BakerySaveRequestDto 서울 = builder()
                .title("서울")
                .city("서울")
                .bakeryCategoryId(1L)
                .description("")
                .content("빵을좋아하는사람만오십시오")
                .district("중분류위치값")
                .mapX(1D)
                .mapY(1D)
                .roadAddress("도로명주소값")
                .signatureMenus("1,2,3")
                .emojiId(1L)
                .build();

        BakerySaveRequestDto 서울특별시 = builder()
                .title("서울")
                .city("서울특별시")
                .bakeryCategoryId(1L)
                .description("")
                .content("빵을좋아하는사람만오십시오")
                .district("중분류위치값")
                .mapX(1D)
                .mapY(1D)
                .roadAddress("도로명주소값")
                .signatureMenus("1,2,3")
                .emojiId(1L)
                .build();

        final User user = new User(
                1L,
                NickName.valueOf("테스트유저"),
                Email.valueOf("test@breadgood.com"),
                "1234",
                1L,
                null,
                null,
                Role.USER
        );

        final BakeryCategory category = new BakeryCategory(
                2L,
                "카테고리2",
                "카테고리2 설명입니다.",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                "#000000",
                ImageUrl.from("https://test.breadgood.com/path1/img.png"),
                2
        );

        final Emoji emoji = new Emoji(
                1L,
                "emojiName",
                "img.url",
                1
        );

        final BreadStyle breadStyle = new BreadStyle(
                1L,
                "담백",
                "담백빵 내용",
                ImageUrl.from("https://test.domain.com/path1/img.png"),
                ImageUrl.from("https://test.domain.com/path1/img.png"),
                "#FFFFFF");

        final Bakery bakery = new Bakery(
                1L,
                "1번 빵집",
                "1번 빵집 설명입니다.",
                1L,
                null,
                null,
                1L
        );
        // when
        when(s3Service.upload((MultipartFile[]) any(), anyString())).thenReturn(new S3UploadResponseDto("", Lists.newArrayList()));
        when(userService.findById(any())).thenReturn(new UserResponseDto(user));
        when(bakeryCategoryService.findById(any())).thenReturn(new BakeryCategoryResponseDto(category));
        when(emojiService.findById(any())).thenReturn(new EmojiResponseDto(emoji));
        when(breadStyleService.findById(any())).thenReturn(new BreadStyleResponseDto(breadStyle));
        when(bakeryRepository.save(any())).thenReturn(bakery);

        //then
        final Long actual = bakeryService.save(null, 서울, null);
        assertThat(actual).isEqualTo(1L);

        // then
        final Long actual2 = bakeryService.save(null, 서울특별시, null);
        assertThat(actual2).isEqualTo(1L);
    }

    @ParameterizedTest(name = "지역 등록 예외 테스트 : {arguments}")
    @ValueSource(strings = {"대구광역시", "강원도"})
    public void saveValidate(String candidate) {
        assertThatThrownBy(() ->
                bakeryService.save(
                        null,
                        builder()
                                .city(candidate)
                                .build(),
                        null
                )
        ).isInstanceOf(IllegalCityException.class);
    }
}