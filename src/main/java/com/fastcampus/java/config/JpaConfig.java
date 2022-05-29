package com.fastcampus.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration      // 여기는 설정파일에대한 것이 들어갑니다
@EnableJpaAuditing  // JPA에 대해서 감시를 활성화시키겠다
public class JpaConfig {
}
