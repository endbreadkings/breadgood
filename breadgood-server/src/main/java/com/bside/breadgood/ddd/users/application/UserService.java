package com.bside.breadgood.ddd.users.application;

import com.bside.breadgood.authentication.oauth2.dto.Oauth2UserSingUpRequestDto;
import com.bside.breadgood.ddd.breadstyles.application.BreadStyleService;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import com.bside.breadgood.ddd.users.application.exception.DuplicateUserNickNameException;
import com.bside.breadgood.ddd.users.application.exception.OnlySocialLinkException;
import com.bside.breadgood.ddd.users.application.exception.UserNotFoundException;
import com.bside.breadgood.ddd.users.domain.User;
import com.bside.breadgood.ddd.users.domain.WithdrawalUser;
import com.bside.breadgood.ddd.users.infra.InitUserData;
import com.bside.breadgood.ddd.users.infra.UserRepository;
import com.bside.breadgood.ddd.users.infra.WithdrawalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static io.jsonwebtoken.lang.Collections.isEmpty;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;
    private final BreadStyleService breadStyleService;
    private final TermsTypeService termsTypeService;

    public UserResponseDto findById(Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id", Long.toString(userId)));
        if (user.isGuest()) {
            return new UserResponseDto(user);
        }
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(user.getBreadStyle());
        return new UserResponseDto(user, breadStyleResponseDto);
    }

    public Map<Long, UserInfoResponseDto> findAllById(Set<Long> userIds) {
        final List<User> users = userRepository.findAllById(userIds);
        final Map<Long, BreadStyleResponseDto> breadMap = breadStyleService.findAllById(getBreadStyleIds(users));

        return users.stream()
                .map(user ->
                        UserInfoResponseDto.valueOf(breadMap.get(user.getBreadStyle()), user)
                )
                .collect(toMap(UserInfoResponseDto::getId, Function.identity()));
    }

    private Set<Long> getBreadStyleIds(List<User> users) {
        if (isEmpty(users)) {
            return emptySet();
        }
        return users.stream().map(User::getBreadStyle).collect(toSet());
    }

    /**
     * 유저아이디의 해당하는 별명과, 프로필 사진을 조회
     * 조회 되지 않을 경우, 탈퇴한 회원인지 확인하여 정해진 탈퇴 전용 별명과 URL 을 가져온다.
     * 조회도 되지 않고 만약 탈퇴한 회원이 아닌 경우 UserNotFoundException 예외가 발생한다.
     */
    public UserInfoResponseDto findUserInfoById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);

        // 존재한다면
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(user.getBreadStyle());
            return UserInfoResponseDto.builder()
                    .breadStyleId(breadStyleResponseDto.getId())
                    .breadStyleName(breadStyleResponseDto.getName())
                    .breadStyleColor(breadStyleResponseDto.getColor())
                    .profileImgUrl(breadStyleResponseDto.getProfileImgUrl())
                    .userId(user.getId())
                    .nickName(user.getNickName())
                    .withdrawal(true)
                    .build();
        }

        // 존재하지 않고, 탈퇴한 회원이라면 기본 UserInfo
        if (isWithdrawal(userId)) {
            return UserInfoResponseDto.getDefault();
        }

        throw new UserNotFoundException("id", Long.toString(userId));

    }

//    @Transactional(readOnly = true)
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("email", email));
//    }

    public boolean duplicateNickName(String nickName) {
        return userRepository.existsByNickName(nickName, PageRequest.of(0, 1)).size() > 0;
    }

    public boolean duplicateNickName(String nickName, Long id) {
        return userRepository.existsByNickNameAndIdIsNot(nickName, id, PageRequest.of(0, 1)).size() > 0;
    }

    @Transactional
    public Long socialGuestSignUp(Long userId, Oauth2UserSingUpRequestDto dto) {
        final String nickName = dto.getNickName();

        // 별명 중복체크
        if (duplicateNickName(nickName)) {
            throw new DuplicateUserNickNameException();
        }

        final User fetchedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", Long.toString(userId)));

        //  해당 user 가 소셜 provider 가 있어야 함.
        if (fetchedUser.getSocialLink() == null || StringUtils.isEmpty(fetchedUser.getSocialLink().getProviderId())) {
            throw new OnlySocialLinkException();

        }

        final List<Long> termsTypeIds = dto.getTermsTypeIds();

        // 필수 약관을 모두 선택 했는지 확인
        termsTypeService.checkRequiredTermsTypes(termsTypeIds);

        // 필수 약관 조회
        final List<TermsTypeResponseDto> termsTypeResponseDtoList = termsTypeService.findByIds(termsTypeIds);

        // 빵스타일 조회
        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(dto.getBreadStyleId());

        fetchedUser.socialGuestSignUp(nickName, breadStyleResponseDto, termsTypeResponseDtoList);

        return userId;

    }

    @Transactional
    public UserResponseDto updateNickName(Long userId, String nickName) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("id", Long.toString(userId)));
        if (duplicateNickName(nickName, userId)) {
            throw new DuplicateUserNickNameException();
        }
        user.updateNickName(nickName);

        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(user.getBreadStyle());
        return new UserResponseDto(user, breadStyleResponseDto);
    }

    @Transactional
    public UserResponseDto updateBreadStyleId(Long userId, Long breadStyleId) {

        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", Long.toString(userId)));

        final BreadStyleResponseDto breadStyleResponseDto = breadStyleService.findById(breadStyleId);

        user.updateBreadStyle(breadStyleResponseDto);

        return new UserResponseDto(user, breadStyleResponseDto);
    }

    @Transactional
    public Long withdrawal(Long id) {
        if (userRepository.findById(id).isPresent()) return 0L;

        // 회원 에그리거트에서 삭제
        userRepository.deleteById(id);

        // 회원 탈퇴 테이블에 등록
        final WithdrawalUser withdrawalUser = WithdrawalUser.getInstanceByUserId(id);

        return withdrawalUserRepository.save(withdrawalUser).getId();

    }

    public boolean isWithdrawal(Long userId) {
        final List<WithdrawalUser> withdrawalUsers =
                withdrawalUserRepository.existsWithdrawalUserByUser(userId, PageRequest.of(0, 1));
        return withdrawalUsers != null && withdrawalUsers.size() > 0;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initData() {
        if (isExistUser()) return;

        userRepository.saveAll(new InitUserData().get());
    }

    private boolean isExistUser() {
        return userRepository.count() > 0;
    }

}
