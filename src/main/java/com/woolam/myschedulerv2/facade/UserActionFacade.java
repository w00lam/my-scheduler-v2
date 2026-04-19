package com.woolam.myschedulerv2.facade;

import com.woolam.myschedulerv2.comment.dto.CommentCreateRequest;
import com.woolam.myschedulerv2.comment.dto.CommentCreateResponse;
import com.woolam.myschedulerv2.comment.dto.CommentGetResponse;
import com.woolam.myschedulerv2.comment.entity.Comment;
import com.woolam.myschedulerv2.comment.service.CommentService;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.schedule.dto.ScheduleGetAllResponse;
import com.woolam.myschedulerv2.schedule.dto.ScheduleGetOneResponse;
import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.schedule.service.ScheduleService;
import com.woolam.myschedulerv2.user.entitiy.User;
import com.woolam.myschedulerv2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>일정 도메인의 복합 조회 로직을 처리하는 애플리케이션 서비스(Facade) 클래스입니다.</p>
 *
 * <p>주요 역할:
 * <ul>
 * <li>일정 서비스와 댓글 서비스의 데이터를 조합하여 응답용 DTO를 생성합니다.</li>
 * <li>서비스 간의 직접적인 순환 참조를 방지하기 위한 중계 계층 역할을 수행합니다.</li>
 * </ul>
 * </p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-18
 */
@Service
@RequiredArgsConstructor
public class UserActionFacade {
    private final UserService userService;
    private final CommentService commentService;
    private final ScheduleService scheduleService;

    /**
     * <p>사용자의 일정 목록을 페이징하여 조회하며, 각 일정에 포함된 댓글의 총 개수를 합산하여 반환합니다.</p>
     *
     * <p>조회 흐름:
     * <ol>
     * <li>{@link ScheduleService}를 통해 사용자 ID에 해당하는 일정 엔티티 목록을 조회합니다.</li>
     * <li>조회된 각 일정에 대해 {@link CommentService}를 호출하여 실시간 댓글 개수를 산출합니다.</li>
     * <li>일정 엔티티와 댓글 개수를 {@link ScheduleGetAllResponse} DTO로 변환하여 반환합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId   조회할 사용자의 고유 식별자
     * @param pageable 페이징 및 정렬 정보를 담은 객체
     * @return 댓글 개수가 포함된 일정 목록 응답 (Page 형태)
     */
    @Transactional(readOnly = true)
    public Page<ScheduleGetAllResponse> getSchedulesWithCommentCount(Long userId, Pageable pageable) {
        Page<Schedule> schedules = scheduleService.findAllByUserId(userId, pageable);

        return schedules.map(schedule -> {
            long commentCount = commentService.countByScheduleId(schedule.getId());

            return ScheduleGetAllResponse.from(schedule, commentCount);
        });
    }

    /**
     * <p>일정의 상세 정보와 해당 일정에 작성된 모든 댓글 목록을 함께 조회합니다.</p>
     *
     * <p>조회 흐름:
     * <ol>
     * <li>{@link ScheduleService}를 통해 사용자 ID와 일정 ID로 일정을 조회하며, 권한을 검증합니다.</li>
     * <li>{@link CommentService}를 통해 해당 일정에 등록된 모든 댓글 목록을 조회합니다.</li>
     * <li>조회된 일정 정보와 댓글 목록을 {@link ScheduleGetOneResponse} DTO로 조합하여 반환합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId     요청한 사용자의 고유 식별자
     * @param scheduleId 조회할 일정의 고유 식별자
     * @return 상세 일정 정보와 댓글 목록이 포함된 응답 객체
     * @throws ServiceException 일정이 존재하지 않거나 해당 사용자의 일정이 아닐 경우 발생
     */
    @Transactional(readOnly = true)
    public ScheduleGetOneResponse getScheduleWithComments(Long userId, Long scheduleId) {
        Schedule schedule = scheduleService.findByIdAndUserIdOrThrow(scheduleId, userId);

        List<Comment> comments = commentService.getComments(schedule);

        List<CommentGetResponse> commentsResponses = comments.stream()
                .map(CommentGetResponse::from)
                .toList();

        return ScheduleGetOneResponse.from(schedule, commentsResponses);
    }

    /**
     * 댓글을 생성하기 위한 비즈니스 흐름을 조율합니다.
     * <p>조회 및 생성 흐름:
     * <ol>
     * <li>요청한 사용자와 대상 일정을 식별하여 권한을 검증합니다.</li>
     * <li>검증된 정보로 도메인 엔티티({@link Comment})를 생성합니다.</li>
     * <li>댓글 서비스({@link CommentService})를 통해 데이터를 저장합니다.</li>
     * <li>저장된 엔티티를 응답용 DTO({@link CommentCreateResponse})로 변환하여 반환합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId  댓글을 작성하는 사용자의 ID
     * @param request 댓글 생성을 위한 요청 정보
     * @return 생성된 댓글의 상세 정보 응답 객체
     */
    @Transactional
    public CommentCreateResponse createComment(Long userId, CommentCreateRequest request) {
        User user = userService.findUserOrThrow(userId);
        Schedule schedule = scheduleService.findByIdAndUserIdOrThrow(request.getScheduleId(), user.getId());
        Comment comment = Comment.create(user, schedule, request);

        Comment savedComment = commentService.saveComment(comment);

        return CommentCreateResponse.from(savedComment);
    }

    /**
     * 특정 일정에 등록된 모든 댓글 목록을 조회합니다.
     * <p>조회 흐름:
     * <ol>
     * <li>요청한 사용자가 해당 일정에 대한 접근 권한이 있는지 검증합니다.</li>
     * <li>댓글 서비스({@link CommentService})를 통해 일정에 연결된 모든 댓글을 조회합니다.</li>
     * <li>조회된 댓글 엔티티 목록을 응답용 DTO 목록({@link CommentGetResponse})으로 변환합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId     조회 요청자의 ID
     * @param scheduleId 댓글 목록을 조회할 일정의 ID
     * @return 해당 일정의 댓글 상세 목록 응답 객체 리스트
     */
    @Transactional(readOnly = true)
    public List<CommentGetResponse> getCommentsBySchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleService.findByIdAndUserIdOrThrow(scheduleId, userId);

        List<Comment> comments = commentService.getComments(schedule);

        return comments.stream()
                .map(CommentGetResponse::from)
                .toList();
    }

    /**
     * 특정 일정을 삭제하고, 해당 일정에 연결된 모든 댓글을 함께 제거합니다.
     * <p>삭제 흐름:
     * <ol>
     * <li>댓글 서비스({@link CommentService})를 통해 해당 일정의 모든 댓글을 벌크 삭제합니다.</li>
     * <li>일정 서비스({@link ScheduleService})를 통해 권한을 검증하고 일정을 최종 삭제합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId     삭제 요청자의 ID
     * @param scheduleId 삭제할 일정의 ID
     */
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        commentService.deleteAllByScheduleId(scheduleId);
        scheduleService.delete(userId, scheduleId);
    }

    /**
     * 사용자의 계정을 탈퇴 처리하며, 연관된 모든 일정과 댓글을 정리합니다.
     * <p>삭제 흐름 (데이터 정합성을 위해 자식부터 역순으로 삭제):
     * <ol>
     * <li>댓글 서비스({@link CommentService})를 통해 사용자가 작성한 모든 댓글을 벌크 삭제합니다.</li>
     * <li>일정 서비스({@link ScheduleService})를 통해 사용자가 생성한 모든 일정을 벌크 삭제합니다.</li>
     * <li>유저 서비스({@link UserService})를 통해 최종적으로 사용자 계정을 삭제합니다.</li>
     * </ol>
     * </p>
     *
     * @param userId 삭제할 사용자의 ID
     */
    @Transactional
    public void deleteUserAccount(Long userId) {
        commentService.deleteAllByUserId(userId);
        scheduleService.deleteAllByUserId(userId);
        userService.delete(userId);
    }
}
