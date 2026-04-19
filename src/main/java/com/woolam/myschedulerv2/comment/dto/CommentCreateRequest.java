package com.woolam.myschedulerv2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * <p>댓글 생성 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Getter
public class CommentCreateRequest {
    @NotNull
    private Long scheduleId;

    @NotBlank
    private String content;
}
