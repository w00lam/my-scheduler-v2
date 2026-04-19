package com.woolam.myschedulerv2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * <p>유저 생성 요청 데이터를 담당하는 DTO 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Getter
public class UserCreateRequest {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 20, message = "이름은 20자 이하여야 합니다")
    private String name;

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하입니다.")
    private String password;
}
