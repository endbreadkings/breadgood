package com.bside.breadgood;

import com.bside.breadgood.authentication.AuthRedirectUrisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@EnableConfigurationProperties(AuthRedirectUrisProperties.class)
@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class BreadgoodServerApplication {
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재시각 : {}", LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(BreadgoodServerApplication.class, args);
    }
}
