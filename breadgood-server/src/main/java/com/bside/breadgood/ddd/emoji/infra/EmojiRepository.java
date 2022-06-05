package com.bside.breadgood.ddd.emoji.infra;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmojiRepository extends CrudRepository<Emoji, Long> {

    List<Emoji> findAllByOrderBySortNumberAsc();
}
