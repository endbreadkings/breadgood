package com.bside.breadgood.ddd.emoji.application;

import com.bside.breadgood.ddd.emoji.application.dto.EmojiRequestDto;
import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.application.exception.EmojiNotFoundException;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.emoji.infra.InitEmojiData;
import com.bside.breadgood.s3.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmojiService {

    private final EmojiRepository emojiRepository;

    private final S3Service s3Service;

    private static final int UNIT_OF_SORT_NUMBER = 100;

    @Transactional
    public void initData() {
        emojiRepository.saveAll(new InitEmojiData().get());
    }

    public List<EmojiResponseDto> findAll() {
        return emojiRepository.findAllByOrderBySortNumberAsc().stream()
                .map(EmojiResponseDto::new)
                .collect(Collectors.toList());
    }

    public EmojiResponseDto findById(Long bakeryCategoryId) {
        final Emoji emoji = emojiRepository.findById(bakeryCategoryId)
                .orElseThrow(() -> new EmojiNotFoundException("id", Long.toString(bakeryCategoryId)));

        return new EmojiResponseDto(emoji);
    }

    /**
     * 리뷰 이모지를 저장한다.
     *
     * @param dto {@link EmojiRequestDto}
     * @return {@link EmojiResponseDto}
     */
    @Transactional
    public EmojiResponseDto save(EmojiRequestDto dto, MultipartFile img) {
        String imgPath = s3Service.upload(img, "admin");
        String imgUrl = s3Service.getFileHost() + imgPath;

        Emoji savedEmoji = emojiRepository.save(dto.toEntity(imgUrl, getNextSortNumber()));

        return new EmojiResponseDto(savedEmoji);
    }

    /**
     * 다음 순서의 정렬 번호를 가져온다.
     *
     * @return 다음 정렬 번호
     */
    private int getNextSortNumber() {
        int maxSortNumber = emojiRepository.findMaxSortNumber();
        return maxSortNumber + UNIT_OF_SORT_NUMBER;
    }
}
