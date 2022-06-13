package com.bside.breadgood.ddd.bakery.infra;

import com.bside.breadgood.ddd.bakery.domain.Bakery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BakeryRepository extends CrudRepository<Bakery, Long>/*, BakeryRepositoryCustom */ {

    boolean existsByIdAndUser(Long bakeryId, Long user);

    @Query("select b from Bakery b where b.address.roadAddress = ?1")
    List<Bakery> existsBakeryByRoadAddress(String roadAddress, Pageable pageable);

//    Optional<List<Bakery>> findByBakeryCategory(Long categoryId);

    List<Bakery> findAllByOrderByIdDesc();
}
