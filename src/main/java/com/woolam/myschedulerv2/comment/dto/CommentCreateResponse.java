package com.woolam.myschedulerv2.comment.dto;

import com.woolam.myschedulerv2.comment.entity.Comment;
import com.woolam.myschedulerv2.schedule.dto.ScheduleInfoResponse;
import com.woolam.myschedulerv2.user.dto.UserGetResponse;

import java.time.LocalDateTime;

/**
 * <p>댓글 생성 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
public record CommentCreateResponse(
        Long id,
        UserGetResponse user,
        ScheduleInfoResponse schedule,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentCreateResponse from(Comment comment) {
        UserGetResponse user = UserGetResponse.from(comment.getUser());
        ScheduleInfoResponse schedule = ScheduleInfoResponse.from(comment.getSchedule());

        return new CommentCreateResponse(
                comment.getId(),
                user,
                schedule,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
