package com.bside.breadgood.ddd.bakery.infra;

import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
import com.bside.breadgood.ddd.bakery.domain.Bakery;

import java.util.List;

public interface BakeryRepositoryCustom {
    List<Bakery> findDynamicQueryAdvance(BakerySearchRequestDto searchRequestDto);
}
