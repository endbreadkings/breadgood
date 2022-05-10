package com.bside.breadgood.ddd.breadstyles.infra;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BreadStyleRepository extends CrudRepository<BreadStyle, Long> {

    @Query("select b from BreadStyle b order by b.sortNumber asc")
    List<BreadStyle> findAllOrderBySortNumberAsc();
}
