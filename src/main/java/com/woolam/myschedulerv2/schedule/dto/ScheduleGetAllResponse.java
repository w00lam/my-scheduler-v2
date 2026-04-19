package com.woolam.myschedulerv2.schedule.dto;

import com.woolam.myschedulerv2.schedule.entitiy.Schedule;

import java.time.LocalDateTime;

/**
 * <p>단건 조회 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
public record ScheduleGetAllResponse(
        Long id,
        String title,
        long commentCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ScheduleGetAllResponse from(Schedule schedule, long commentCount) {
        return new ScheduleGetAllResponse(
                schedule.getId(),
                schedule.getTitle(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
