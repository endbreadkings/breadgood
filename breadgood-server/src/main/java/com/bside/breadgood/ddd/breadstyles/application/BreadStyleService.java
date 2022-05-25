package com.bside.breadgood.ddd.breadstyles.application;

import com.bside.breadgood.ddd.breadstyles.application.exception.BreadStyleNotFoundException;
import com.bside.breadgood.ddd.breadstyles.domain.BreadStyle;
import com.bside.breadgood.ddd.breadstyles.infra.BreadStyleRepository;
import com.bside.breadgood.ddd.breadstyles.infra.InitBreadStyleData;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleRequestDto;
import com.bside.breadgood.ddd.breadstyles.ui.dto.BreadStyleResponseDto;
import com.bside.breadgood.s3.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BreadStyleService {

    private final S3Service s3Service;

    private final BreadStyleRepository breadStyleRepository;

    @Transactional
    public void initData() {
        breadStyleRepository.saveAll(new InitBreadStyleData().get());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<BreadStyleResponseDto> findAll() {
        return breadStyleRepository.findAllOrderBySortNumberAsc().stream()
                .map(BreadStyleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BreadStyleResponseDto findById(Long breadStyleId) {
        final BreadStyle breadStyle = breadStyleRepository.findById(breadStyleId)
                .orElseThrow(() -> new BreadStyleNotFoundException("id", Long.toString(breadStyleId)));
        return new BreadStyleResponseDto(breadStyle);
    }

    @Transactional
    public BreadStyleResponseDto save(BreadStyleRequestDto requestDto,
        MultipartFile contentImg, MultipartFile profileImg) {
        String contentImgPath = s3Service.upload(contentImg, "admin");
        String contentImgUrl = s3Service.getFileHost() + contentImgPath;

        String profileImgPath = s3Service.upload(profileImg, "admin");
        String profileImgUrl = s3Service.getFileHost() + profileImgPath;

        BreadStyle breadStyle = requestDto.convertToEntity(contentImgUrl, profileImgUrl);
        final BreadStyle savedBreadStyle = breadStyleRepository.save(breadStyle);

        return new BreadStyleResponseDto(savedBreadStyle);
    }
}
