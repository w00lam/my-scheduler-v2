package com.woolam.myschedulerv2.common.response;

/**
 * <p>API 응답을 통일하기 위한 공통 응답 클래스입니다.</p>
 *
 * <p>
 * status(HTTP 상태 코드), message(응답 메시지), data(응답 데이터)를 포함합니다.
 * </p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public record CommonApiResponse<T>(String message, T data) {
    public static <T> CommonApiResponse<T> success(String message, T data) {
        return new CommonApiResponse<>(message, data);
    }

    public static <T> CommonApiResponse<T> success(String message) {
        return success(message, null);
    }
}
