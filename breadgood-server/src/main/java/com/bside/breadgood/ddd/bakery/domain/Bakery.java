package com.bside.breadgood.ddd.bakery.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import com.bside.breadgood.common.exception.EmptyException;
import com.bside.breadgood.common.exception.WrongValueException;
import com.bside.breadgood.ddd.bakery.application.exception.ReviewDeletionException;
import com.bside.breadgood.ddd.bakery.application.exception.ReviewNotFoundException;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE bakery SET deleted = true WHERE bakery_id=?")
@Where(clause = "deleted=false")
public class Bakery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakery_id")
    private Long id;

    private String title;

    private String description;

    private Long user;

    @Embedded
    private Address address;

    @Embedded
    private Point point;

    private Long bakeryCategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bakery_id")
    private final List<BakeryReview> bakeryReviewList = new ArrayList<>();

    @Builder
    public Bakery(String title, String description, UserResponseDto user,
                  String city, String district, String roadAddress,
                  Double mapX, Double mapY,
                  BakeryCategoryResponseDto bakeryCategory,
                  String content, EmojiResponseDto emoji, List<String> imgUrls, List<String> signatureMenus, String imgHost,
                  BreadStyleResponseDto breadStyle) {


        if (StringUtils.isEmpty(title)) {
            throw new EmptyException("????????? ?????? ????????????.");
        }

        if (user == null || user.getId() == null || user.getId() == 0) {
            throw new EmptyException("?????? ????????? ?????? ????????????.");
        }

        if (StringUtils.isEmpty(city)) {
            throw new EmptyException("????????? ????????? ?????? ?????? ????????????.");
        }

        if (StringUtils.isEmpty(district)) {
            throw new EmptyException("????????? ????????? ?????? ?????? ????????????.");
        }

        if (StringUtils.isEmpty(roadAddress)) {
            throw new EmptyException("?????? ????????? ?????? ?????? ????????????.");
        }

        if (mapX == null || mapX == 0) {
            throw new EmptyException("????????? X?????? ?????? ?????? ????????????.");
        }

        if (mapY == null || mapY == 0) {
            throw new EmptyException("????????? Y?????? ?????? ?????? ????????????.");
        }

        if (bakeryCategory == null || bakeryCategory.getId() == null || bakeryCategory.getId() == 0) {
            throw new EmptyException("?????? ???????????? ?????? ????????????.");
        }

        // ???????????? ?????? ?????? 1??? ?????? 3???
        if (signatureMenus == null || signatureMenus.size() == 0) {
            throw new EmptyException("???????????? ?????? ?????? ????????????.");
        }

        if (signatureMenus.size() > 3) {
            throw new WrongValueException("???????????? ?????? ?????? ?????? 3??? ?????? ???????????????.");
        }

        this.title = title;
        this.description = description;
        this.user = user.getId();
        this.address = Address.builder().roadAddress(roadAddress).city(city).district(district).build();
        this.point = Point.builder().mapX(mapX).mapY(mapY).build();
        this.bakeryCategory = bakeryCategory.getId();
        this.addBakeryReview(user, content, emoji, imgUrls, signatureMenus, imgHost, breadStyle);
    }

    public void addBakeryReview(UserResponseDto user, String content, EmojiResponseDto emoji,
                                List<String> imgUrls, List<String> signatureMenus,
                                String imgHost, BreadStyleResponseDto breadStyle) {

        // ???????????? ?????? ?????? ????????? 3??? ??????
        if (signatureMenus != null && signatureMenus.size() != 0 && signatureMenus.size() > 3) {
            throw new WrongValueException("???????????? ?????? ?????? ?????? 3??? ?????? ???????????????.");
        }

        if (signatureMenus != null) {
            for (String signatureMenu : signatureMenus) {
                if (signatureMenu.length() > 15) {
                    throw new WrongValueException("???????????? ????????? ?????? ?????? ?????? 15??? ?????? ???????????????.");
                }
            }
        }

        final BakeryReview bakeryReview = BakeryReview.builder()
                .content(content)
                .user(user)
                .imgUrls(imgUrls)
                .signatureMenus(signatureMenus)
                .emoji(emoji)
                .breadStyle(breadStyle)
                .imgHost(imgHost)
                .build();

        this.bakeryReviewList.add(bakeryReview);
    }

    public BakeryReview getReview(Long reviewId) {
        return this.bakeryReviewList.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    private void deleteVerification(BakeryReview review) {
        if (this.user.equals(review.getUser())) {
            throw new ReviewDeletionException();
        }
    }

    public void deleteBakeryReview(BakeryReview review) {
        deleteVerification(review);
        this.bakeryReviewList.remove(review);
    }
}
