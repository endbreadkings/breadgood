package com.bside.breadgood.ddd.bakery.application.dto;

import com.bside.breadgood.ddd.bakery.domain.Address;
import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.users.application.dto.UserResponseDto;
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
public class BakeryManagementResponse {
    private Long no;
    private String name;
    private Address address;
    private UserResponseDto userResponseDto;
    private LocalDateTime createdAt;

    public static BakeryManagementResponse valueOf(Bakery entity, UserResponseDto userResponseDto) {
        return new BakeryManagementResponse(entity.getId(), entity.getTitle(), entity.getAddress(), userResponseDto, entity.getCreatedAt());
    }
}
