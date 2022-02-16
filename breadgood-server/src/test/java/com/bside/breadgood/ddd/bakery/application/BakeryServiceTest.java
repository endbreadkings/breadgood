package com.bside.breadgood.ddd.bakery.application;

import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchResponseDto;
import com.bside.breadgood.ddd.bakery.domain.Address;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.bakery.domain.Point;
import com.bside.breadgood.ddd.bakery.infra.BakeryRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BakeryServiceTest {

    BakeryService bakeryService;

    BakeryRepository bakeryRepository;

    UserService userService;

    BakeryCategoryService bakeryCategoryService;

    @BeforeEach
    void setUp() {
        bakeryRepository = Mockito.mock(BakeryRepository.class);
        userService = Mockito.mock(UserService.class);
        bakeryCategoryService = Mockito.mock(BakeryCategoryService.class);
        S3Service s3Service = Mockito.mock(S3Service.class);
        EmojiService emojiService = Mockito.mock(EmojiService.class);
        BreadStyleService breadStyleService = Mockito.mock(BreadStyleService.class);

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
        User user = new User(1L, NickName.valueOf("뛰뛰빵빵"), Email.valueOf("test@breadgood.com"), "1234", 1L, "img.img", null, null, Role.USER);
        UserResponseDto userDto = new UserResponseDto(user);

        Emoji emoji = new Emoji(1L, "emojiName", "img.url", 1);
        EmojiResponseDto emojiDto = new EmojiResponseDto(emoji);

        BreadStyle breadStyle = new BreadStyle(1L,"담백", "담백빵 내용", "img.url", "img.url");
        BreadStyleResponseDto breadStyleDto = new BreadStyleResponseDto(breadStyle);

        bakery1.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);
        bakery2.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);

        return List.of(bakery2, bakery1);
    }

    private void setSearchMockReturns() {
        BakeryCategory bakeryCategory1 = new BakeryCategory(1L, "카테고리1", "카테고리 1의 설명입니다.", "urlurlurl", "urlurlurl", 1);
        BakeryCategory bakeryCategory2 = new BakeryCategory(2L, "카테고리2", "카테고리 2의 설명입니다.", "urlurlurl", "urlurlurl", 2);
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
            .thenReturn(new BakeryCategoryResponseDto(bakeryCategory1));
        when(bakeryCategoryService.findById(2L))
            .thenReturn(new BakeryCategoryResponseDto(bakeryCategory2));
        when(bakeryRepository.findAllOrderByIdDesc())
            .thenReturn(bakeries);
    }

//    @Test
//    void findByIdAndUserId() {
//        final BakeryResponseDto bakeryResponseDto = bakeryService.findByIdAndUserId(1L, 1L);
//        System.out.println(bakeryResponseDto.getBakeryReviews());
//        assertEquals(bakeryResponseDto.getBakeryReviews().get(0).getUserId(), 1L);
//    }
}