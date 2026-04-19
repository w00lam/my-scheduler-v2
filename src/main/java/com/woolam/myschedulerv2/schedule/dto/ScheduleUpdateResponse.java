package com.woolam.myschedulerv2.schedule.dto;

import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.user.dto.UserGetResponse;

import java.time.LocalDateTime;

/**
 * <p>일정 수정 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public record ScheduleUpdateResponse(
        Long id,
        UserGetResponse user,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ScheduleUpdateResponse from(Schedule schedule) {
        UserGetResponse user = UserGetResponse.from(schedule.getUser());

        return new ScheduleUpdateResponse(
                schedule.getId(),
                user,
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
