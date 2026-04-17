package com.woolam.myschedulerv2.schedule.dto;

import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.user.entitiy.User;

import java.time.LocalDateTime;

/**
 * <p>일정 생성 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public record ScheduleCreateResponse(Long id,
                                     User user,
                                     String title,
                                     String content,
                                     LocalDateTime createdAt,
                                     LocalDateTime modifiedAt
) {
    public static ScheduleCreateResponse from(Schedule schedule) {
        return new ScheduleCreateResponse(
                schedule.getId(),
                schedule.getUser(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
