package com.bside.breadgood.ddd.bakerycategory.application;

import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.application.exception.BakeryCategoryNotFoundException;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import com.bside.breadgood.ddd.bakerycategory.infra.InitBakeryCategoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BakeryCategoryService {

    private final BakeryCategoryRepository bakeryCategoryRepository;

    @Transactional
    public void initData() {
        bakeryCategoryRepository.saveAll(new InitBakeryCategoryData().get());
    }

    @Transactional(readOnly = true)
    public List<BakeryCategoryResponseDto> findAll() {
        return bakeryCategoryRepository.findAllOrderBySortNumberAsc().stream()
                .map(BakeryCategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    public BakeryCategoryResponseDto findById(Long bakeryCategoryId) {
        final BakeryCategory bakeryCategory = bakeryCategoryRepository.findById(bakeryCategoryId)
                .orElseThrow(() -> new BakeryCategoryNotFoundException("id", Long.toString(bakeryCategoryId)));

        return new BakeryCategoryResponseDto(bakeryCategory);
    }
}
