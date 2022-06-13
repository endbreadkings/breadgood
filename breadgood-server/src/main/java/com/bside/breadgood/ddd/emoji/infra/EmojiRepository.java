package com.bside.breadgood.ddd.emoji.infra;

import com.bside.breadgood.ddd.emoji.domain.Emoji;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmojiRepository extends CrudRepository<Emoji, Long> {

    List<Emoji> findAllByOrderBySortNumberAsc();

    /**
     * 정렬 순서 최대값을 가져온다. 저장된 데이터가 없으면 0을 리턴한다.
     *
     * @return 정렬 순서 최대값
     */
    @Query("select coalesce(max(e.sortNumber), 0) from Emoji e")
    int findMaxSortNumber();
}
