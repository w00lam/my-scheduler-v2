package com.woolam.myschedulerv2.auth.dto;

import com.woolam.myschedulerv2.user.entitiy.User;

/**
 * 로그인한 사용자의 정보를 세션에 저장하기 위한 인증 전용 DTO입니다.
 * <p>보안 및 성능을 위해 민감한 정보(비밀번호 등)를 제외하고,
 * 세션 관리에 필요한 최소한의 식별 정보만을 포함합니다.</p>
 *
 * @param userId 식별자(PK)
 * @param email  사용자 이메일
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-19
 */
public record LoginUserDto(Long userId, String email) {
    public static LoginUserDto from(User user) {
        return new LoginUserDto(user.getId(), user.getEmail());
    }
}
