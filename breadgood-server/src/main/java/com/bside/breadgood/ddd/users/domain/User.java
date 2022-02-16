package com.bside.breadgood.ddd.users.domain;

import com.bside.breadgood.common.domain.BaseEntity;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeResponseDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @ElementCollection
    private List<UserTerms> userTerms;

    @Embedded
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

    public User(String nickName, String email, String password, Long breadStyle, List<UserTerms> userTerms, Role role) {
        this.nickName = NickName.valueOf(nickName);
        this.email = Email.valueOf(email);
        this.password = password;
        this.breadStyle = breadStyle;
        this.userTerms = userTerms;
        this.role = role;
    }


    public void socialGuestSignUp(String nickName, BreadStyleResponseDto breadStyle, List<TermsTypeResponseDto> termsTypes) {
        this.nickName = NickName.valueOf(nickName);
        this.breadStyle = breadStyle.getId();
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

    public boolean isGuest() {
        return this.role == Role.GUEST;
    }
}
