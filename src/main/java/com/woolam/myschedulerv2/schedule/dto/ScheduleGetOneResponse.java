package com.woolam.myschedulerv2.schedule.dto;

import com.woolam.myschedulerv2.comment.dto.CommentGetResponse;
import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.user.dto.UserGetResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>단건 조회 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public record ScheduleGetOneResponse(
        Long id,
        UserGetResponse user,
        String title,
        String content,
        List<CommentGetResponse> comments,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ScheduleGetOneResponse from(Schedule schedule, List<CommentGetResponse> comments) {
        UserGetResponse user = UserGetResponse.from(schedule.getUser());

        return new ScheduleGetOneResponse(
                schedule.getId(),
                user,
                schedule.getTitle(),
                schedule.getContent(),
                comments,
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
