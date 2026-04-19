package com.woolam.myschedulerv2.user.controller;

import com.woolam.myschedulerv2.facade.UserActionFacade;
import com.woolam.myschedulerv2.auth.dto.LoginUserDto;
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
    private final UserActionFacade userActionFacade;

    @PostMapping
    public ResponseEntity<CommonApiResponse<UserCreateResponse>> signUp(
            @Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse.success("회원 가입에 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<UserGetResponse>>> getUsers() {
        List<UserGetResponse> response = userService.getUsers();

        return ResponseEntity.ok(CommonApiResponse.success("전체 조회에 성공했습니다.", response));
    }

    @GetMapping("/me")
    public ResponseEntity<CommonApiResponse<UserGetResponse>> getMyInfo(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        UserGetResponse response = userService.getMyInfo(loginUser.userId());

        return ResponseEntity.ok(CommonApiResponse.success("프로필 조회에 성공했습니다.", response));
    }

    @PatchMapping("/me")
    public ResponseEntity<CommonApiResponse<UserUpdateResponse>> updateUser(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.update(loginUser.userId(), request);

        return ResponseEntity.ok(CommonApiResponse.success("프로필 정보를 수정했습니다", response));
    }

    @DeleteMapping("/me")
    public ResponseEntity<CommonApiResponse<Void>> deleteUser(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        userActionFacade.deleteUserAccount(loginUser.userId());

        return ResponseEntity.ok(CommonApiResponse.success("회원 탈퇴했습니다."));
    }
}
