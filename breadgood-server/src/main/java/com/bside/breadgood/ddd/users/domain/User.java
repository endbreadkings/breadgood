package com.bside.breadgood.ddd.users.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickName", column = @Column(name = "nickName"))
    })
    private NickName nickName;

    @Column(nullable = false, unique = true)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email"))
    })

    private Email email;

    private String password;

    private Long breadStyle;

    private String profileImg;

    @ElementCollection
    private List<UserTerms> userTerms;

    @Enumerated
    private SocialLink socialLink;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public User(String email, AuthProvider provider, String providerId, Role role) {
        final SocialLink socialLink = SocialLink.builder().provider(provider).providerId(providerId).build();
        this.email = Email.valueOf(email);
        this.socialLink = socialLink;
        this.role = role;
    }

    public User(String nickName, String email, String password, Long breadStyle, String profileImg, List<UserTerms> userTerms, Role role) {
        this.nickName = NickName.valueOf(nickName);
        this.email = Email.valueOf(email);
        this.password = password;
        this.breadStyle = breadStyle;
        this.profileImg = profileImg;
        this.userTerms = userTerms;
        this.role = role;
    }


    public void socialGuestSignUp(String nickName, BreadStyleResponseDto breadStyle, List<TermsTypeResponseDto> termsTypes) {
        this.nickName = NickName.valueOf(nickName);
        this.breadStyle = breadStyle.getId();
        this.profileImg = breadStyle.getProfileImgUrl();
        setUserTerms(termsTypes);
        this.role = Role.USER;
    }

    private void setUserTerms(List<TermsTypeResponseDto> termsTypes) {
        final LocalDateTime now = LocalDateTime.now();

        final List<UserTerms> userTerms = termsTypes.stream()
                .map(termsType -> UserTerms.builder()
                        .termsAgree(true)
                        .termsDate(now)
                        .termsType(termsType.getId())
                        .build())
                .collect(Collectors.toList());

        this.userTerms = userTerms;
    }

    public void updateNickName(String nickName) {
        this.nickName = NickName.valueOf(nickName);
    }

    public void updateBreadStyle(BreadStyleResponseDto breadStyleResponseDto) {
        this.breadStyle = breadStyleResponseDto.getId();
        this.profileImg = breadStyleResponseDto.getProfileImgUrl();
    }

    public String getNickName() {
        if (nickName == null) {
            return null;
        }
        return nickName.getNickName();
    }

    public String getEmail() {
        if (email == null) {
            return null;
        }
        return email.getEmail();
    }
}
