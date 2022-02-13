package com.bside.breadgood.jwt.domain;

import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Builder
    public RefreshToken(UserResponseDto user, String token, LocalDateTime expiryDate) {
        this.user = user.getId();
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String updateToken(String token) {
        this.token = token;
        return token;
    }
}
