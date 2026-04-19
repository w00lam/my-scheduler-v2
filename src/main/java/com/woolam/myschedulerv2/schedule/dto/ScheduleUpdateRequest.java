package com.woolam.myschedulerv2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * <p>일정 수정 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 */
@Getter
public class ScheduleUpdateRequest {
    @Size(max = 30, message = "제목은 30자 이하여야 합니다")
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @Size(max = 200, message = "내용은 200자 이하여야 합니다")
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
}
