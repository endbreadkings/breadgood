package com.bside.breadgood.ddd.users.infra;

import com.bside.breadgood.ddd.bakery.domain.Bakery;
import com.bside.breadgood.ddd.users.domain.AuthProvider;
import com.bside.breadgood.ddd.users.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.socialLink.provider = ?1 and u.socialLink.providerId = ?2")
    Optional<User> findBySocialLink(AuthProvider provider, String providerId);

    @Query("select u from User u WHERE u.nickName.nickName = ?1")
    List<User> existsByNickName(String nickName, Pageable pageable);

    @Query("select u from User u WHERE u.nickName.nickName = ?1 and u.id <> ?2")
    List<User> existsByNickNameAndIdIsNot(String nickName, Long id, Pageable pageable);
}
