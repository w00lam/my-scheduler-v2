package com.woolam.myschedulerv2.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * <p>BCrypt 기반 비밀번호 인코딩 및 검증을 담당하는 컴포넌트입니다.</p>
 *
 * <p>회원가입 시 평문 비밀번호를 안전한 해시 값으로 변환하고
 * 로그인 시 입력된 비밀번호와 저장된 해시 값을 비교하는 역할을 합니다.</p>
 *
 * <p>BCrypt는 단방향 해시 알고리즘으로 한 번 암호화된 값은 복호화가 불가능합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Component
public class PasswordEncoder {
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
