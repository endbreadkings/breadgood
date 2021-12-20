//package com.bside.breadgood.ddd.bakery.infra;
//
//import com.bside.breadgood.ddd.bakery.domain.Bakery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class BakeryRepositorySupport extends QuerydslRepositorySupport {
//
//    private final JPAQueryFactory queryFactory;
//
//    public BakeryRepositorySupport(JPAQueryFactory queryFactory) {
//        super(Bakery.class);
//        this.queryFactory = queryFactory;
//    }
//}