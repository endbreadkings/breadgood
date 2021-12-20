package com.bside.breadgood.ddd.bakery.application;

import com.bside.breadgood.ddd.bakery.application.dto.BakeryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BakeryServiceTest {

    @Autowired
    BakeryService bakeryService;

    @Test
    void 서울_로_시작하는_주소인지_확인() {
        String containsWord = "서울";
        String address = "서울특별시 중구 을지로3가 229-1 ";
        String address2 = "서울특별시 중구 을지로3가 229-1 ";
        assertTrue(address.contains(containsWord));
        assertTrue(address2.contains(containsWord));
    }

    @Test
    void 특정_숫자_정렬_확인() {
        List<Long> userUid = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        Comparator<Long> comparator = (o1, o2) -> {
            if (o1 == 2) return -1;
            if (o2 == 2) return 1;
            return o2.compareTo(o1);
        };

        Collections.sort(userUid, comparator);

        System.out.println(userUid);

//        List<String> res =  userUid.stream().sorted()
//                .filter(u -> !u.equals("uid2"))
//                .collect(ArrayList::new,
//                        (l, s) ->{if (l.isEmpty())l.add("uid2");l.add(s);},
//                        List::addAll );
//
//        System.out.println(res);
    }

//    @Test
//    void findByIdAndUserId() {
//        final BakeryResponseDto bakeryResponseDto = bakeryService.findByIdAndUserId(1L, 1L);
//        System.out.println(bakeryResponseDto.getBakeryReviews());
//        assertEquals(bakeryResponseDto.getBakeryReviews().get(0).getUserId(), 1L);
//    }
}