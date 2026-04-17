package com.woolam.myschedulerv2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * <p>유저 수정 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Getter
public class UserUpdateRequest {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 20, message = "이름은 20자 이하여야 합니다")
    private String name;
}
