package com.woolam.myschedulerv2.auth.controller;

import com.woolam.myschedulerv2.auth.dto.LoginRequest;
import com.woolam.myschedulerv2.auth.service.AuthService;
import com.woolam.myschedulerv2.common.response.CommonApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>인증/인가 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * "/api/auth" 경로로 들어오는 요청을 담당합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest request, HttpSession session) {
        authService.login(request, session);

        return ResponseEntity.ok(CommonApiResponse.success("로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonApiResponse<Void>> logout(HttpSession session) {
        authService.logout(session);

        return ResponseEntity.ok(CommonApiResponse.success("로그아웃 성공"));
    }
}
