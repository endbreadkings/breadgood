package com.bside.breadgood.ddd.termstype.application;

import com.bside.breadgood.ddd.termstype.application.excetion.IllegalTermsTypeException;
import com.bside.breadgood.ddd.termstype.application.excetion.NotChoiceRequireTermsTypeException;
import com.bside.breadgood.ddd.termstype.application.excetion.TermsNotFoundException;
import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.infra.InitTermsTypeData;
import com.bside.breadgood.ddd.termstype.infra.TermsTypeRepository;
import com.bside.breadgood.ddd.termstype.ui.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermsTypeService {

    private final TermsTypeRepository termsTypeRepository;

    @Transactional
    public TermsTypeResponseDto save(TermsTypeSaveRequestDto termsTypeSaveRequestDto) {
        final int nextSortNumber = termsTypeRepository.findNextSortNumber();
        final TermsType termsType = termsTypeRepository.save(termsTypeSaveRequestDto.toEntity(nextSortNumber));
        return TermsTypeResponseDto.valueOf(termsType);
    }

    @Transactional
    public void initData() {
        termsTypeRepository.saveAll(new InitTermsTypeData().get());
    }


    // 적용중인 약관 리스트 조회
    @Transactional(readOnly = true)
    public List<ActiveTermsResponseDto> findActiveList() {
        // 오늘 일자보다 이전이면서, 가장 최신의 약관

        List<ActiveTermsResponseDto> activeTermsResponseDtoList = new ArrayList<>();

        termsTypeRepository.findAllByOrderBySortNumberAsc(/*sortBySortNumber()*/).forEach(termsType -> {

            final Terms activeTerms = termsType.getTerms().stream()
                    .filter(terms -> terms.getExecutionDate().isBefore(LocalDate.now()))
                    .max(Comparator.comparing(Terms::getExecutionDate))
                    .orElseThrow(() -> new TermsNotFoundException("약관타입", "적용중인"));

            activeTermsResponseDtoList.add(new ActiveTermsResponseDto(termsType, activeTerms));
        });

        return activeTermsResponseDtoList;
    }

    @Transactional(readOnly = true)
    public void checkRequiredTermsTypes(List<Long> termsTypeIds) {

        final List<TermsType> termsTypeList = termsTypeRepository.findAllByOrderBySortNumberAsc();

        HashMap<Long, TermsType> hm = new HashMap<>();
        for (TermsType termsType : termsTypeList) {
            hm.put(termsType.getId(), termsType);
        }

        for (Long termsTypeId : termsTypeIds) {
            hm.remove(termsTypeId);
        }

        for (Long key : hm.keySet()) {
            final TermsType termsType = hm.get(key);
            if (termsType == null) {
                // 잘못된 약관 타입
                throw new IllegalTermsTypeException();
            }

            if (termsType.isRequired()) {
                // 필수 동의 약관 미선택
                throw new NotChoiceRequireTermsTypeException();
            }

        }
    }

    @Transactional(readOnly = true)
    public List<TermsTypeResponseDto> findByIds(List<Long> termsTypeIds) {

        return termsTypeIds.stream().map(id -> {
            final TermsType termsType = termsTypeRepository.findById(id).orElseThrow(() -> new TermsNotFoundException("id", String.valueOf(id)));
            return TermsTypeResponseDto.builder()
                    .id(termsType.getId())
                    .required(termsType.isRequired())
                    .name(termsType.getName())
                    .build();
        }).collect(Collectors.toList());
    }

    public TermsTypeInfoResponseDto findById(Long termsTypeId) {
        final TermsType termsType = findByIdOrElseThrow(termsTypeId);
        return new TermsTypeInfoResponseDto(termsType);
    }

    public Object findByIdAndTermsId(Long termsTypeId, Long termsId) {
        final TermsType termsType = findByIdOrElseThrow(termsTypeId);
        final Terms terms = termsType.getTerms()
                .stream()
                .filter(t -> t.getId().equals(termsId))
                .findFirst()
                .orElseThrow(() -> new TermsNotFoundException("id", String.valueOf(termsTypeId)));
        return new TermsDetailResponseDto(termsType, terms);
    }

    @Transactional
    public void addTerm(TermsSaveRequestDto request) {
        final TermsType termsType = findByIdOrElseThrow(request.getTermsTypeId());
        termsType.addTerms(request.toEntity());
    }

    @Transactional(readOnly = true)
    public TermsType findByIdOrElseThrow(Long id) {
        return termsTypeRepository.findById(id)
                .orElseThrow(() -> new TermsNotFoundException("id", String.valueOf(id)));
    }

//    private Sort sortBySortNumber() {
//        return new Sort(new Sort.Order(Sort.Direction.ASC, "sortNumber"));
//    }

}
