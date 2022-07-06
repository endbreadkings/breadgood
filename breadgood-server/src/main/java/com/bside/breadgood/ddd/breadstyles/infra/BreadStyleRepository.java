package com.bside.breadgood.ddd.breadstyles.infra;

import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BreadStyleRepository extends JpaRepository<BreadStyle, Long> {

    List<BreadStyle> findAllByOrderBySortNumberAsc();

    @Query("select coalesce(max(b.sortNumber), 0) from BreadStyle b")
    int findMaxSortNumber();
}
