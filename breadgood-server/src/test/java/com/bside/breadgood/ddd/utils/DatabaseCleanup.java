package com.bside.breadgood.ddd.utils;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * author : haedoang
 * date : 2022/02/13
 * description :
 */
@Service
@ActiveProfiles("test")
@Slf4j
public class DatabaseCleanup implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleanup.class);

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() throws Exception {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .collect(toList());

    }


    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        // generator reset
        entityManager.createNativeQuery("alter table bakery alter column bakery_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table bakery_category alter column bakery_category_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table bakery_review alter column bakery_review_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table bread_style alter column bread_style_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table emoji alter column emoji_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table refresh_token alter column id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table terms alter column terms_id restart with  1").executeUpdate();
        entityManager.createNativeQuery("alter table terms_type alter column terms_type_id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table user alter column id restart with 1").executeUpdate();
        entityManager.createNativeQuery("alter table withdrawal_user alter column id restart with 1").executeUpdate();


        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

        logger.info("database cleanup successfully");
    }
}
