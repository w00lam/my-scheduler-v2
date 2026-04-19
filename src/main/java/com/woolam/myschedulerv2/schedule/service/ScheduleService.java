package com.woolam.myschedulerv2.schedule.service;

import com.woolam.myschedulerv2.common.exception.ErrorCode;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.schedule.dto.*;
import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.schedule.repository.ScheduleRepository;
import com.woolam.myschedulerv2.user.entitiy.User;
import com.woolam.myschedulerv2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>일정 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 일정의 생성, 조회, 변경, 삭제 기능을 제공합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponse createSchedule(Long userId, ScheduleCreateRequest request) {
        User user = userService.findUserOrThrow(userId);

        Schedule schedule = Schedule.create(user, request);
        scheduleRepository.save(schedule);

        return ScheduleCreateResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public Page<Schedule> findAllByUserId(Long userId, Pageable pageable) {
        return scheduleRepository.findAllByUserId(userId, pageable);
    }

    @Transactional
    public ScheduleUpdateResponse update(Long userId, Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = this.findByIdAndUserIdOrThrow(scheduleId, userId);
        schedule.update(request);

        return ScheduleUpdateResponse.from(schedule);
    }

    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = this.findByIdAndUserIdOrThrow(userId, scheduleId);
        scheduleRepository.delete(schedule);
    }

    public void deleteAllByUserId(Long userId) {
        scheduleRepository.deleteAllByUserId(userId);
    }

    public Schedule findByIdAndUserIdOrThrow(Long scheduleId, Long userId) {
        return scheduleRepository.findByIdAndUserId(scheduleId, userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
