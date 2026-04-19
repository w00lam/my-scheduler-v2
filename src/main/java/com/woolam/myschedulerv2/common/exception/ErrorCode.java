package com.woolam.myschedulerv2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * <p>애플리케이션 전반에서 사용하는 에러 코드를 정의하는 Enum입니다.</p>
 *
 * <p>
 * 각 에러 코드는 HTTP 상태 코드(status), 기본 메시지(message)를 포함합니다.
 * </p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@Getter
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다"),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
