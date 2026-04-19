package com.woolam.myschedulerv2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>댓글 수정 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Getter
public class CommentUpdateRequest {
    @NotBlank
    @Length(max = 100)
    private String content;
}
