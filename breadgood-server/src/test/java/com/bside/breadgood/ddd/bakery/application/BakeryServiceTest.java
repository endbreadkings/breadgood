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
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.ddd.users.domain.Email;
import com.bside.breadgood.ddd.users.domain.NickName;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.fixtures.bakery.BakeryFixture;
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
import static com.bside.breadgood.ddd.utils.EntityReflectionUtils.setId;
import static com.bside.breadgood.fixtures.bakery.BakeryFixture.빵집1;
import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.빵에집중;
import static com.bside.breadgood.fixtures.bakerycategory.BakeryCategoryFixture.음료와빵;
import static com.bside.breadgood.fixtures.breadstyle.BreadStyleFixture.달콤_200;
import static com.bside.breadgood.fixtures.emoji.EmojiFixture.이모지1;
import static com.bside.breadgood.fixtures.user.UserFixture.테스트유저;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        assertEquals("빵에집중", response.get(0).getCategoryTitle());
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

        BreadStyle breadStyle = new BreadStyle(1L, "담백", "담백빵 내용", ImageUrl.from("https://test.domain.com/path1/img.png"), ImageUrl.from("https://test.domain.com/path1/img.png"), "#FFFFFF", 400);
        BreadStyleResponseDto breadStyleDto = new BreadStyleResponseDto(breadStyle);

        bakery1.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);
        bakery2.addBakeryReview(userDto, "리뷰 내용이지롱. 10글자 채워야되네...", emojiDto, null, List.of("메뉴1", "메뉴2"), "hosthost", breadStyleDto);

        return List.of(bakery2, bakery1);
    }

    private void setSearchMockReturns() {
        UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.builder()
                .nickName("테스트유저")
                .userId(1L)
                .profileImgUrl("img.img")
                .breadStyleId(1L)
                .breadStyleName("담백")
                .withdrawal(true)
                .build();
        List<Bakery> bakeries = getDummyBakeries();

        // when
        when(userService.findUserInfoById(1L))
                .thenReturn(userInfoResponseDto);
        when(bakeryCategoryService.findById(1L))
                .thenReturn(new BakeryCategoryResponseDto(빵에집중));
        when(bakeryCategoryService.findById(2L))
                .thenReturn(new BakeryCategoryResponseDto(음료와빵));
        when(bakeryRepository.findAllByOrderByIdDesc())
                .thenReturn(bakeries);
    }

    @Test
    @DisplayName("베이커리는 서울특별시만 등록 가능하다")
    public void saveBakerySeoul() throws NoSuchMethodException {
        // given
        BakerySaveRequestDto 서울특별시 = BakerySaveRequestDto.builder()
                .title("서울")
                .city("서울특별시")
                .bakeryCategoryId(1L)
                .description("")
                .content("빵을좋아하는사람만오십시오")
                .district("중분류위치값")
                .mapX(1D)
                .mapY(1D)
                .roadAddress("도로명주소값")
                .signatureMenus(Lists.newArrayList("1", "2", "3"))
                .emojiId(1L)
                .build();

        final BakeryService bakeryService = new BakeryService(
                bakeryRepository, s3Service, userService, bakeryCategoryService, emojiService, breadStyleService);

        // when
        when(s3Service.upload((MultipartFile[]) any(), anyString())).thenReturn(new S3UploadResponseDto("", Lists.newArrayList()));
        when(userService.findById(any())).thenReturn(new UserResponseDto(테스트유저));

        setId(빵에집중, BakeryCategory.class, 1L);

        when(bakeryCategoryService.findById(any())).thenReturn(new BakeryCategoryResponseDto(빵에집중));
        when(emojiService.findById(any())).thenReturn(new EmojiResponseDto(이모지1));

        setId(달콤_200, BreadStyle.class, 1L);

        when(breadStyleService.findById(any())).thenReturn(new BreadStyleResponseDto(달콤_200));
        when(bakeryRepository.save(any())).thenReturn(빵집1);

        //then
        final Long actual = bakeryService.save(null, 서울특별시, null);
        assertThat(actual).isEqualTo(1L);


    }

    @ParameterizedTest(name = "지역 등록 예외 테스트 : {arguments}")
    @ValueSource(strings = {"대구광역시", "강원도", "서울시 마포구", "서울", "서울시 광진구"})
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

    @Test
    @DisplayName("빵집 삭제하기")
    public void deleteBakery() {
        // given
        given(bakeryRepository.findById(anyLong())).willReturn(Optional.of(빵집1));

        // when
        bakeryService.delete(빵집1.getId());

        // then
        verify(bakeryRepository).findById(1L);
    }
}