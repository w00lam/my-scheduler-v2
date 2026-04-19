package com.woolam.myschedulerv2.common.exception;

import lombok.Getter;

/**
 * <p>비즈니스 로직에서 발생하는 예외를 처리하기 위한 커스텀 예외 클래스입니다.</p>
 *
 * <p>
 * ErrorCode를 기반으로 예외를 생성하며
 * GlobalExceptionHandler에서 해당 예외를 받아
 * 일관된 API 응답으로 변환합니다.
 * </p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-10
 */
@Getter
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
