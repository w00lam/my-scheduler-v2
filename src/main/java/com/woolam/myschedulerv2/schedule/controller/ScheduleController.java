package com.woolam.myschedulerv2.schedule.controller;

import com.woolam.myschedulerv2.common.response.CommonApiResponse;
import com.woolam.myschedulerv2.schedule.dto.*;
import com.woolam.myschedulerv2.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<CommonApiResponse<ScheduleCreateResponse>> createSchedule(
            @SessionAttribute(name = "loginUser") Long userId,
            @Valid @RequestBody ScheduleCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse
                        .success(
                                "일정 생성에 성공했습니다.",
                                scheduleService.createSchedule(userId, request)
                        )
                );
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<ScheduleGetAllResponse>>> getSchedules(
            @SessionAttribute(name = "loginUser") Long userId
    ) {
        return ResponseEntity.ok(
                CommonApiResponse.success("전체 일정 조회에 성공했습니다.", scheduleService.getSchedules(userId))
        );
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<ScheduleGetOneResponse>> getSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser") Long userId
    ) {
        return ResponseEntity.ok(
                CommonApiResponse.success(
                        "해당 일정 조회에 성공했습니다.",
                        scheduleService.getSchedule(userId, scheduleId)
                )
        );
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<ScheduleUpdateResponse>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request,
            @SessionAttribute(name = "loginUser") Long userId
    ) {
        return ResponseEntity.ok(
                CommonApiResponse.success("일정을 수정했습니다.", scheduleService.update(userId, scheduleId, request))
        );
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CommonApiResponse<Void>> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser") Long userId
    ) {
        scheduleService.delete(userId, scheduleId);

        return ResponseEntity.ok(
                CommonApiResponse.success("일정을 삭제했습니다.")
        );
    }
}
