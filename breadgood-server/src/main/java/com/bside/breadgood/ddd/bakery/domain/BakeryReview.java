package com.bside.breadgood.ddd.bakery.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE bakery_review SET deleted = true, bakery_id=null WHERE bakery_review_id=?")
@Where(clause = "deleted=false")
public class BakeryReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakery_review_id")
    private Long id;

    @Embedded
    private ReviewContent content;

    private Long emoji;

    @ElementCollection
    private List<String> imgUrls;

    private String imgHost;

    @Embedded
    private SignatureMenus signatureMenus;

    private Long user;

    // 당시 좋아한 빵 성향
    @Embedded
    private BreadStyle breadStyle;

    @Builder
    public BakeryReview(String content, EmojiResponseDto emoji, List<String> imgUrls, List<String> signatureMenus, UserResponseDto user, String imgHost, BreadStyleResponseDto breadStyle) {

        // 리뷰글, 이모지 필수
        // 인증사진은 최대 10개까지 선택 가능하다

        if (user == null || user.getId() == null || user.getId() == 0) {
            throw new EmptyException("리뷰 등록자 값이 없습니다.");
        }

        if (emoji == null || emoji.getId() == null || emoji.getId() == 0) {
            throw new EmptyException("리뷰 이모지 값이 없습니다.");
        }

        if (imgUrls != null && imgUrls.size() != 0 && imgUrls.size() > 10) {
            throw new WrongValueException("인증사진은 최대 10개 까지 가능합니다.");
        }

        if (imgUrls != null && imgUrls.size() != 0 && StringUtils.isEmpty(imgHost)) {
            throw new WrongValueException("업로드된 사진은 있는데, 업로드 호스트가 존재 하지 않습니다.");
        }

        if (breadStyle == null || breadStyle.getId() == null || breadStyle.getId() == 0) {
            throw new EmptyException("리뷰 등록자 최애빵 스타일 값이 없습니다.");
        }

        this.content = ReviewContent.valueOf(content);
        this.emoji = emoji.getId();
        this.imgUrls = imgUrls;
        this.user = user.getId();
        this.imgHost = imgHost;
        this.signatureMenus = SignatureMenus.valueOf(signatureMenus);
        this.breadStyle = BreadStyle.builder()
                .breadStyleImgUrl(breadStyle.getImgUrl())
                .breadStyleName(breadStyle.getName())
                .build();
    }

    public String getContent() {
        if (content == null || StringUtils.isEmpty(content.getContent())) {
            return null;
        }
        return content.getContent();
    }

    public List<String> getSignatureMenus() {
        if (signatureMenus == null) {
            return null;
        }

        return signatureMenus.getSignatureMenus()
                .stream()
                .map(SignatureMenu::getSignatureMenu)
                .collect(toUnmodifiableList());
    }

    public List<String> getImgUrls() {
        if (imgUrls == null || imgUrls.size() == 0) {
            return null;

        }
        return imgUrls.stream()
                .map(imageUrl -> this.getImgHost() + imageUrl)
                .collect(toList());
    }
}
