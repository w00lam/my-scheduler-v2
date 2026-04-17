package com.woolam.myschedulerv2.user.controller;

import com.woolam.myschedulerv2.common.exception.ErrorCode;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.common.response.CommonApiResponse;
import com.woolam.myschedulerv2.user.dto.*;
import com.woolam.myschedulerv2.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>유저 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * "/api/users" 경로로 들어오는 요청을 담당합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonApiResponse<UserCreateResponse>> signUp(
            @Valid @RequestBody UserCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse
                        .success("회원 가입에 성공했습니다.", userService.signUp(request)
                        )
                );
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<UserGetResponse>>> getUsers() {
        return ResponseEntity.ok(
                CommonApiResponse.success("전체 조회에 성공했습니다.", userService.getUsers())
        );
    }

    @GetMapping("/me")
    public ResponseEntity<CommonApiResponse<UserGetResponse>> getMyInfo(
            @SessionAttribute(name = "loginUser") Long userId) {
        return ResponseEntity.ok(
                CommonApiResponse.success("프로필 조회에 성공했습니다.", userService.getMyInfo(userId))
        );
    }

    @PatchMapping("/me")
    public ResponseEntity<CommonApiResponse<UserUpdateResponse>> updateUser(
            @SessionAttribute(name = "loginUser") Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(
                CommonApiResponse.success("프로필 정보를 수정했습니다", userService.update(userId, request))
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<CommonApiResponse<Void>> deleteUser(
            @SessionAttribute(name = "loginUser") Long userId) {
        userService.delete(userId);

        return ResponseEntity.ok(
                CommonApiResponse.success("회원 탈퇴했습니다.")
        );
    }
}
