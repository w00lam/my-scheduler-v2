package com.woolam.myschedulerv2.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * <p>로그인 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Getter
public class LoginRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
