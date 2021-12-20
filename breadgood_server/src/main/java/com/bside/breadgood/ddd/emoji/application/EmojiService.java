package com.bside.breadgood.ddd.emoji.application;

import com.bside.breadgood.ddd.emoji.application.dto.EmojiResponseDto;
import com.bside.breadgood.ddd.emoji.application.exception.EmojiNotFoundException;
import com.bside.breadgood.ddd.emoji.domain.Emoji;
import com.bside.breadgood.ddd.emoji.infra.EmojiRepository;
import com.bside.breadgood.ddd.emoji.infra.InitEmojiData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmojiService {

    private final EmojiRepository emojiRepository;

    @Transactional
    public void initData() {
        emojiRepository.saveAll(new InitEmojiData().get());
    }

    public List<EmojiResponseDto> findAll() {
        return emojiRepository.findAllOrderByIdDesc().stream()
                .map(EmojiResponseDto::new)
                .collect(Collectors.toList());
    }

    public EmojiResponseDto findById(Long bakeryCategoryId) {
        final Emoji emoji = emojiRepository.findById(bakeryCategoryId)
                .orElseThrow(() -> new EmojiNotFoundException("id", Long.toString(bakeryCategoryId)));

        return new EmojiResponseDto(emoji);
    }
}
