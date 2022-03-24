package com.bside.breadgood.ddd.users.domain;


import com.bside.breadgood.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class WithdrawalUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_user_id")
    private Long id;

    private Long user;


    private WithdrawalUser(Long user) {
        this.user = user;
    }

    public static WithdrawalUser getInstanceByUserId(Long UserId) {
        return new WithdrawalUser(UserId);
    }
}
