package com.bside.breadgood.ddd.bakery.application;

import com.bside.breadgood.ddd.bakery.application.dto.BakeryResponseDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryReviewRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryReviewResponseDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySaveRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchResponseDto;
import com.bside.breadgood.ddd.bakery.application.enums.BakerySortType;
import com.bside.breadgood.ddd.bakery.application.dto.CheckDuplicateBakeryResponseDto;
import com.bside.breadgood.ddd.bakery.application.exception.BakeryNotFoundException;
import com.bside.breadgood.ddd.bakery.application.exception.DuplicateBakeryException;
import com.bside.breadgood.ddd.bakery.application.exception.IllegalCityException;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.bakery.domain.BakeryReview;
import com.bside.breadgood.ddd.bakery.infra.BakeryRepository;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.EmojiService;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.users.application.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.application.UserService;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.s3.application.S3Service;
import com.bside.breadgood.s3.application.dto.S3UploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BakeryService {
    private static final String SEOUL_WORD = "서울";
    private static final String SEOUL_CITY_WORD = "서울특별시";
    private final BakeryRepository bakeryRepository;
    private final S3Service s3Service;
    private final UserService userService;
    private final BakeryCategoryService bakeryCategoryService;
    private final EmojiService emojiService;
    private final BreadStyleService breadStyleService;

//    private final BakeryRepositorySupport bakeryRepositorySupport;

    /**
     * - 서울특별시 만 가능
     */
    @Transactional
    public Long save(Long registerUserId, BakerySaveRequestDto dto, MultipartFile[] files) {
        validateSave(dto, files);
        final S3UploadResponseDto s3UploadResponseDto = s3Service.upload(files, "bakery");
        final String fileHost = s3UploadResponseDto.getFileHost();
        final List<String> filePaths = s3UploadResponseDto.getFilePaths();
        final UserResponseDto userResponseDto = userService.findById(registerUserId);
        final BakeryCategoryResponseDto bakeryCategoryResponseDto = bakeryCategoryService.findById(dto.getBakeryCategoryId());
        final EmojiResponseDto emojiResponseDto = emojiService.findById(dto.getEmojiId());
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(userResponseDto.getBreadStyleId());

        final Bakery bakery = Bakery.builder()
                .bakeryCategory(bakeryCategoryResponseDto)
                .city(dto.getCity())
                .content(dto.getContent())
                .description(dto.getDescription())
                .district(dto.getDistrict())
                .emoji(emojiResponseDto)
                .imgHost(fileHost)
                .imgUrls(filePaths)
                .mapX(dto.getMapX())
                .mapY(dto.getMapY())
                .roadAddress(dto.getRoadAddress())
                .signatureMenus(dto.getSignatureMenus())
                .title(dto.getTitle())
                .user(userResponseDto)
                .breadStyle(breadStyleResponseDto)
                .build();

        return bakeryRepository.save(bakery).getId();
    }

    private void validateSave(BakerySaveRequestDto dto, MultipartFile[] files) {
        if (StringUtils.isEmpty(dto.getCity()) || !dto.getCity().equals(SEOUL_CITY_WORD) || !dto.getCity().contains(SEOUL_WORD)) {
            throw new IllegalCityException(dto.getCity());
        }
        if (checkDuplicatedRoadAddress(dto.getRoadAddress())) {
            throw new DuplicateBakeryException();
        }
        // TODO 인증사진은 최대 10개까지 선택 가능하다
    }

    private boolean checkDuplicatedRoadAddress(String roadAddress) {
        final List<Bakery> bakeries = bakeryRepository.existsBakeryByRoadAddress(roadAddress, PageRequest.of(0, 1));
        return bakeries != null && !bakeries.isEmpty();
    }

    /**
     * - 리뷰글, 이모지 필수값
     * - 인증사진은 최대 10개까지 선택 가능하다
     */
    @Transactional
    public boolean addReview(Long bakeryId, Long registerUserId, BakeryReviewRequestDto dto, MultipartFile[] files) {
        // 이미 리뷰 등록한 사람인지 체크
        checkDuplicateBakeryReview(bakeryId, registerUserId);

        final UserResponseDto userResponseDto = userService.findById(registerUserId);
        final EmojiResponseDto emojiResponseDto = emojiService.findById(dto.getEmojiId());
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(userResponseDto.getBreadStyleId());
        final Bakery bakery = bakeryRepository.findById(bakeryId).orElseThrow(BakeryNotFoundException::new);

        if (files != null && files.length > 0) {
            final S3UploadResponseDto s3UploadResponseDto = s3Service.upload(files, "bakery");
            final String fileHost = s3UploadResponseDto.getFileHost();
            final List<String> filePaths = s3UploadResponseDto.getFilePaths();
            bakery.addBakeryReview(userResponseDto, dto.getContent(), emojiResponseDto, filePaths, dto.getSignatureMenus(), fileHost, breadStyleResponseDto);
            return true;
        }

        bakery.addBakeryReview(userResponseDto, dto.getContent(), emojiResponseDto, null, dto.getSignatureMenus(), null, breadStyleResponseDto);
        return true;
    }


    /**
     * 해당 아이디의 빵집을 조회
     * 자신이 등록한 리뷰가 있으면 가장 상위에 노출된다.
     */
    public BakeryResponseDto findByIdAndUserId(Long id, Long currentUserId) {

        // 빵집 최초 등록자 정보 (최초 등록자 닉네임)
        final Bakery bakery = bakeryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        final Long userId = bakery.getUser();

        // 최초 등록자 닉네임, 프로필 사진 조회
        final UserInfoResponseDto bakeryUserInfo = userService.findUserInfoById(userId);
        final List<BakeryReviewResponseDto> bakeryReviews = bakery.getBakeryReviewList().stream().map(bakeryReview -> {
            final EmojiResponseDto emojiResponseDto = emojiService.findById(bakeryReview.getEmoji());
            final UserInfoResponseDto reviewUserInfo = userService.findUserInfoById(bakeryReview.getUser());
            return BakeryReviewResponseDto.builder().bakeryReview(bakeryReview).emojiResponseDto(emojiResponseDto).userInfoResponseDto(reviewUserInfo).build();
        }).sorted((b1, b2) -> {
            if (b1.getUserId().compareTo(currentUserId) == 0) return -1;
            if (b2.getUserId().compareTo(currentUserId) == 0) return 1;
            return b2.getUserId().compareTo(b1.getUserId());
        }).collect(Collectors.toList());

        // 빵집 이름, 주소 정보,
        final String title = bakery.getTitle();
        // 빵집 카테고리 정보
        final BakeryCategoryResponseDto bakeryCategoryResponseDto = bakeryCategoryService.findById(bakery.getBakeryCategory());
        final String categoryTitle = bakeryCategoryResponseDto.getTitle();
        // 빵집 도로명 주소
        final String roadAddress = bakery.getAddress().getRoadAddress();


        return BakeryResponseDto.builder()
                .bakeryReviews(bakeryReviews)
                .userInfoResponseDto(bakeryUserInfo)
                .roadAddress(roadAddress)
                .categoryTitle(categoryTitle)
                .title(title)
                .build();
    }


    private void checkDuplicateBakeryReview(Long bakeryId, Long registerUserId) {
        if (bakeryRepository.existsByIdAndUser(bakeryId, registerUserId)) {
            throw new DuplicateBakeryException();
        }
    }

    // 중복 체크
    public CheckDuplicateBakeryResponseDto checkDuplicateByRoadAddress(String roadAddress) {
        final List<Bakery> bakeries = bakeryRepository.existsBakeryByRoadAddress(roadAddress, PageRequest.of(0, 1));

        final boolean idDuplicate = bakeries != null && !bakeries.isEmpty();
        String nickName = "";

        if (idDuplicate) {
            final UserInfoResponseDto userInfo = userService.findUserInfoById(bakeries.get(0).getUser());
            nickName = userInfo.getNickName();

            return new CheckDuplicateBakeryResponseDto(true, nickName, bakeries.get(0).getId());
        }

        return new CheckDuplicateBakeryResponseDto(false, nickName, null);
    }

    @Transactional(readOnly = true)
    public List<BakerySearchResponseDto> search(BakerySearchRequestDto dto) {
        final String city = dto.getCity();
        final String district = dto.getDistrict();
        final Set<Long> bakeryCategories = dto.getBakeryCategories();

        if (Objects.nonNull(bakeryCategories) && bakeryCategories.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Bakery> bakeries = bakeryRepository.findAll();
        Stream<Bakery> bakeryStream = bakeries.stream();

        bakeryStream = filterBakeryCategories(bakeryStream, bakeryCategories);
        bakeryStream = filterCity(bakeryStream, city);
        bakeryStream = filterDistrict(bakeryStream, district);
        bakeryStream = orderBySortType(bakeryStream, dto.getBakerySortType());

        return convertBakerySearchResponseDtos(bakeryStream);
    }

    private Stream<Bakery> filterDistrict(Stream<Bakery> bakeryStream, String district) {
        if (StringUtils.hasText(district)) {
            return bakeryStream.filter(bakery -> bakery.getAddress().getDistrict().equals(district));
        }
        return bakeryStream;
    }

    private Stream<Bakery> filterBakeryCategories(Stream<Bakery> bakeryStream, Set<Long> bakeryCategories) {
        if (Objects.nonNull(bakeryCategories)) {
            return bakeryStream.filter(bakery -> bakeryCategories.contains(bakery.getBakeryCategory()));
        }
        return bakeryStream;
    }

    private Stream<Bakery> filterCity(Stream<Bakery> bakeryStream, String city) {
        if (StringUtils.hasText(city)) {
            validateCityContainsWord(city);
            return bakeryStream.filter(bakery -> bakery.getAddress().getCity().equals(city));
        }
        return bakeryStream;
    }

    /**
     * 스트림을 정렬 타입에 따라 정렬한다.
     *
     * @param bakeryStream 빵집 리스트 스트림
     * @param bakerySortType 정렬 타입
     * @return 정렬된 스트림
     */
    private Stream<Bakery> orderBySortType(Stream<Bakery> bakeryStream, BakerySortType bakerySortType) {
        if (bakerySortType == BakerySortType.ID_DESC || bakerySortType == null) {
            return bakeryStream.sorted(Comparator.comparingLong(Bakery::getId).reversed());
        } else if (bakerySortType == BakerySortType.ID_ASC) {
            return bakeryStream.sorted(Comparator.comparingLong(Bakery::getId));
        }
        throw new IllegalArgumentException();
    }

    private void validateCityContainsWord(String city) {
        if (!city.contains(SEOUL_WORD)) {
            throw new IllegalCityException(city);
        }
    }

    /**
     * 빵집을 검색할 경우,빵집 정보에서 리뷰는 가장 최신이면서 빵집의 리뷰와 시그니처 메뉴가 모두 있는 리뷰 하나를 가진다.
     */
    private List<BakerySearchResponseDto> convertBakerySearchResponseDtos(Stream<Bakery> bakeryStream) {
        return bakeryStream.map(bakery -> {
            final BakeryReview bakeryReview = bakery.getBakeryReviewList()
                    .stream()
                    .sorted(Comparator.comparingLong(BakeryReview::getId).reversed())
                    .filter(br -> !br.getSignatureMenus().isEmpty() && !StringUtils.isEmpty(br.getContent()))
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new);
            final BakeryCategoryResponseDto bakeryCategoryResponseDto = bakeryCategoryService.findById(bakery.getBakeryCategory());
            final UserInfoResponseDto userInfo = userService.findUserInfoById(bakery.getUser());
            return new BakerySearchResponseDto(bakery, bakeryReview, bakeryCategoryResponseDto, userInfo);
        }).collect(Collectors.toList());
    }


    @Transactional
    public void initData() {
        List<Bakery> bakeries = new ArrayList<>();

        final String fileHost = "https://globaltwojob.com/";
        final List<String> filePaths = List.of("wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg");
        final UserResponseDto userResponseDto = userService.findById(3L);
        final BakeryCategoryResponseDto bakeryCategoryResponseDto = bakeryCategoryService.findById(1L);
        final BakeryCategoryResponseDto bakeryCategoryResponseDto2 = bakeryCategoryService.findById(2L);
        final EmojiResponseDto emojiResponseDto = emojiService.findById(1L);
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(1L);


        {
            String city = "서울특별시";
            String content = "루브래드는 정말 맛있어요.";
            String description = "휴무일 : 매주일요일 2째주월요일 4째주월요일\n" +
                    "\n" +
                    "정성스러울 루, bread\n" +
                    "정성을 다해 매일 빵을 준비합니다.\n" +
                    "천연발효종을 이용한 다양한 하드빵과\n" +
                    "크림, 팥을 듬뿍 넣은 부드러운 빵들,\n" +
                    "커피와 함께 즐기실 수 있는 달콤한 쿠키류와 케이크를\n" +
                    "만나보실수 있습니다~";
            String district = "강서구";
            double mapX = 37.554074;
            double mapY = 126.859918;
            String roadAddress = "서울특별시 강서구 화곡로64길 70";
            List<String> signatureMenus = Arrays.asList("단팥빵", "소시지빵");
            String title = "루브레드";

            final Bakery bakery = Bakery.builder()
                    .bakeryCategory(bakeryCategoryResponseDto)
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

            bakery.addBakeryReview(userService.findById(4L), "잉 너무 맛있는 걸욧??", emojiResponseDto, List.of("wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg"), Arrays.asList("딸기크림케이크", "케이크"), fileHost, breadStyleResponseDto);
            bakery.addBakeryReview(userService.findById(2L), "정말 정말 그냥 그럭 저럭", emojiResponseDto, List.of("wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg"), null, fileHost, breadStyleResponseDto);
            bakery.addBakeryReview(userService.findById(1L), "소보루 존맛 소보루 존맛 !!", emojiService.findById(2L), List.of("wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg"), Arrays.asList("산딸기 케이크", "소보루빵"), fileHost, breadStyleResponseDto);
            bakery.addBakeryReview(userService.findById(2L), "냥냥냥 냥냥냥 냥냥냥 !!", emojiService.findById(2L), List.of("wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg", "wp-content/uploads/2020/03/pixabay-252777_1920-1.jpg"), Arrays.asList("산딸기 케이크", "소보루빵"), fileHost, breadStyleResponseDto);
            bakery.addBakeryReview(userService.findById(5L), "냥냥냥 냥냥냥 냥냥냥 냥냥냥 !!", emojiService.findById(2L), filePaths, Arrays.asList("산딸기 케이크", "소보루빵"), fileHost, breadStyleResponseDto);

            bakeries.add(bakery);
        }

        {
            String city = "서울특별시";
            String content = "통밀한빵 정말 맛있어요.";
            String description = "";
            String district = "강서구";
            double mapX = 37.554965;
            double mapY = 126.855886;
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
                    .user(userService.findById(1L))
                    .breadStyle(breadStyleResponseDto)
                    .build();

            bakeries.add(bakery);
            bakery.addBakeryReview(userService.findById(3L), "통밀맛 존맛이에요 진짜루...", emojiService.findById(1L), filePaths, Arrays.asList("산딸기 케이크", "소보루빵"), fileHost, breadStyleResponseDto);
        }

        bakeryRepository.saveAll(bakeries);
    }
}
