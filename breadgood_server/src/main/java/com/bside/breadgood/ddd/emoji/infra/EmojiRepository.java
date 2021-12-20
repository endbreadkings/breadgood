package com.bside.breadgood.ddd.emoji.infra;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmojiRepository extends CrudRepository<Emoji, Long> {

    @Query("select b from Emoji b order by b.sortNumber asc")
    List<Emoji> findAllOrderByIdDesc();
}
