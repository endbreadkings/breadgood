package com.bside.breadgood.ddd.users.infra;

import com.bside.breadgood.ddd.users.domain.WithdrawalUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WithdrawalUserRepository extends CrudRepository<WithdrawalUser, Long> {


    @Query("select w from WithdrawalUser w where w.user = ?1")
    List<WithdrawalUser> existsWithdrawalUserByUser(Long userId, Pageable pageable);
}
