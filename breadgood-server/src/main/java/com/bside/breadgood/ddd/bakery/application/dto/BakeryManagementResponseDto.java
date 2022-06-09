package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.domain.Address;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.users.application.dto.UserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * author : haedoang
 * date : 2022/06/08
 * description :
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BakeryManagementResponseDto {
    private Long id;
    private String title;
    private Address address;
    private UserInfoResponseDto user;
    private LocalDateTime createdAt;

    public static BakeryManagementResponseDto valueOf(Bakery entity, UserInfoResponseDto nickname) {
        return new BakeryManagementResponseDto(entity.getId(), entity.getTitle(), entity.getAddress(), nickname, entity.getCreatedAt());
    }
}
