package com.bside.breadgood.ddd.breadstyles.application;

import com.bside.breadgood.ddd.breadstyles.application.exception.BreadStyleNotFoundException;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.infra.InitBreadStyleData;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreadStyleService {

    private final BreadStyleRepository breadStyleRepository;

    @Transactional
    public void initData() {
        breadStyleRepository.saveAll(new InitBreadStyleData().get());
    }

    public List<BreadStyleResponseDto> findAll() {
        return breadStyleRepository.findAllOrderByIdDesc().stream()
                .map(BreadStyleResponseDto::new)
                .collect(Collectors.toList());
    }

    public BreadStyleResponseDto findById(Long breadStyleId) {
        final BreadStyle breadStyle = breadStyleRepository.findById(breadStyleId).orElseThrow(() -> new BreadStyleNotFoundException("id", Long.toString(breadStyleId)));
        return new BreadStyleResponseDto(breadStyle);
    }
}