package com.bside.breadgood.ddd.bakerycategory.application;

import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryRequestDto;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import com.bside.breadgood.ddd.bakerycategory.application.exception.BakeryCategoryNotFoundException;
import com.bside.breadgood.ddd.bakerycategory.domain.BakeryCategory;
import com.bside.breadgood.ddd.bakerycategory.infra.BakeryCategoryRepository;
import com.bside.breadgood.ddd.bakerycategory.infra.InitBakeryCategoryData;
import com.bside.breadgood.s3.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BakeryCategoryService {
    private static final String DIR_PATH = "bakeryCategory";

    private final BakeryCategoryRepository bakeryCategoryRepository;
    private final S3Service s3Service;

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

    @Transactional
    public BakeryCategoryResponseDto saveBakeryCategory(BakeryCategoryRequestDto request) {

        final String fileHost = s3Service.getFileHost();
        final String markerImgUrl = fileHost + s3Service.upload(request.getMarkerImg(), DIR_PATH);
        final String titleColoredImgUrl = fileHost + s3Service.upload(request.getTitleColoredImg(), DIR_PATH);
        final String titleUncoloredImgUrl = fileHost + s3Service.upload(request.getTitleUncoloredImg(), DIR_PATH);

        final BakeryCategory saved = bakeryCategoryRepository.save(BakeryCategory.builder()
                .color(request.getColor())
                .content(request.getContent())
                .markerImgUrl(markerImgUrl)
                .sortNumber(bakeryCategoryRepository.nextSortNumber())
                .title(request.getTitle())
                .titleColoredImgUrl(titleColoredImgUrl)
                .titleUncoloredImgUrl(titleUncoloredImgUrl)
                .build());
        return new BakeryCategoryResponseDto(saved);

    }
}
