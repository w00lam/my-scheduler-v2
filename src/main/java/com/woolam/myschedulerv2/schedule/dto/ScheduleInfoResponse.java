package com.woolam.myschedulerv2.schedule.dto;

import com.woolam.myschedulerv2.schedule.entitiy.Schedule;

/**
 * <p>댓글 생성 후 간단한 스케줄 데이터 응답을 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-18
 */
public record ScheduleInfoResponse(Long id, String title) {
    public static ScheduleInfoResponse from(Schedule schedule) {
        return new ScheduleInfoResponse(schedule.getId(), schedule.getTitle());
    }
}
