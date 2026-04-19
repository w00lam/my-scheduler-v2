package com.woolam.myschedulerv2.schedule.controller;

import com.woolam.myschedulerv2.facade.UserActionFacade;
import com.woolam.myschedulerv2.auth.dto.LoginUserDto;
import com.woolam.myschedulerv2.common.response.CommonApiResponse;
import com.woolam.myschedulerv2.schedule.dto.*;
import com.woolam.myschedulerv2.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * <p>일정 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * "/api/schedules" 경로로 들어오는 요청을 담당합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserActionFacade userActionFacade;

    @PostMapping
    public ResponseEntity<CommonApiResponse<ScheduleCreateResponse>> createSchedule(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @Valid @RequestBody ScheduleCreateRequest request) {
        ScheduleCreateResponse response = scheduleService.createSchedule(loginUser.userId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse.success("일정 생성에 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<Page<ScheduleGetAllResponse>>> getSchedules(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @PageableDefault(
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        Page<ScheduleGetAllResponse> response = userActionFacade.getSchedulesWithCommentCount(
                loginUser.userId(),
                pageable
        );

        return ResponseEntity.ok(CommonApiResponse.success("전체 일정 조회에 성공했습니다.", response));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<ScheduleGetOneResponse>> getSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        ScheduleGetOneResponse response = userActionFacade.getScheduleWithComments(scheduleId, loginUser.userId());

        return ResponseEntity.ok(CommonApiResponse.success("해당 일정 조회에 성공했습니다.", response));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<ScheduleUpdateResponse>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request,
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        ScheduleUpdateResponse response = scheduleService.update(loginUser.userId(), scheduleId, request);

        return ResponseEntity.ok(CommonApiResponse.success("일정을 수정했습니다.", response));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<Void>> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        userActionFacade.deleteSchedule(loginUser.userId(), scheduleId);

        return ResponseEntity.ok(CommonApiResponse.success("일정을 삭제했습니다."));
    }
}
