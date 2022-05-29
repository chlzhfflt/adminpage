package com.fastcampus.java.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> { // 로그인한 사용자를 감시하는 역할로 사용

    @Override
    public Optional<String> getCurrentAuditor() { // 현재 감시자의 이름을 리턴하게 됨
        return Optional.of("AdminServer");
    }
}
