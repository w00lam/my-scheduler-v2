package com.woolam.myschedulerv2.user.dto;

import com.woolam.myschedulerv2.user.entitiy.User;

import java.time.LocalDateTime;

/**
 * <p>유저 조회 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
public record UserGetResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserGetResponse from(User user) {
        return new UserGetResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
