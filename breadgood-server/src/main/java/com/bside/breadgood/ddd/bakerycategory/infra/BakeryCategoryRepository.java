package com.bside.breadgood.ddd.bakerycategory.infra;

import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BakeryCategoryRepository extends CrudRepository<BakeryCategory, Long> {

    @Query("select b from BakeryCategory b order by b.sortNumber")
    List<BakeryCategory> findAllOrderBySortNumberAsc();

    @Query("select max(b.sortNumber) from BakeryCategory b ")
    Integer maxSortNumber();

}
