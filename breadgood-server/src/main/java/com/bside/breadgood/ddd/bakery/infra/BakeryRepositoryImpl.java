//package com.bside.breadgood.ddd.bakery.infra;
//
//import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
//import com.bside.breadgood.ddd.bakery.domain.Bakery;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//import static com.bside.breadgood.ddd.bakery.domain.QBakery.bakery;
//
//@RequiredArgsConstructor
//public class BakeryRepositoryImpl implements BakeryRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Bakery> findDynamicQueryAdvance(BakerySearchRequestDto searchRequestDto) {
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        final String district = searchRequestDto.getDistrict();
//        final Long bakeryCategory = searchRequestDto.getBakeryCategory();
//
//        if (bakery.address != null && !StringUtils.isEmpty(district)) {
//            builder.and(bakery.address.district.eq(district));
//        }
//        if (bakeryCategory != null) {
//            builder.and(bakery.bakeryCategory.eq(bakeryCategory));
//        }
//
//        return queryFactory
//                .selectFrom(bakery)
//                .where(builder)
//                .fetch();
//
//    }
//}
