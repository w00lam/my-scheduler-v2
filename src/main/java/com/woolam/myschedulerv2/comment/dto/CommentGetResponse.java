package com.woolam.myschedulerv2.comment.dto;

import com.woolam.myschedulerv2.comment.entity.Comment;

import java.time.LocalDateTime;

/**
 * <p>댓글 조회 완료 후 응답 데이터를 담당하는 Record입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
public record CommentGetResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentGetResponse from(Comment comment) {
        return new CommentGetResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
