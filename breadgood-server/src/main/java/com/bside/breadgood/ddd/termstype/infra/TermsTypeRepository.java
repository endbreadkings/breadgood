package com.bside.breadgood.ddd.termstype.infra;

import com.bside.breadgood.ddd.termstype.domain.TermsType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TermsTypeRepository extends JpaRepository<TermsType, Long> {

    List<TermsType> findAll(Sort sort);

    List<TermsType> findAllByOrderBySortNumberAsc();

//    @Query("select tp from TermsType tp where tp.id IN :termsTypeIds")
//    List<TermsType> findByIds(List<Long> termsTypeIds);

    @Query(nativeQuery = true, value = "select IFNULL(max(sort_number), 0) + 100 from terms_type")
    int findNextSortNumber();
}
