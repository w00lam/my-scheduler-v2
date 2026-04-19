package com.woolam.myschedulerv2.comment.service;

import com.woolam.myschedulerv2.comment.dto.CommentGetResponse;
import com.woolam.myschedulerv2.comment.dto.CommentUpdateRequest;
import com.woolam.myschedulerv2.comment.entity.Comment;
import com.woolam.myschedulerv2.comment.repository.CommentRepository;
import com.woolam.myschedulerv2.common.exception.ErrorCode;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>댓글 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 댓글의 생성, 조회, 변경, 삭제 기능을 제공합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getComments(Schedule schedule) {
        return commentRepository.findAllByScheduleId(schedule.getId());
    }

    @Transactional
    public CommentGetResponse update(Long commentId, Long userId, CommentUpdateRequest request) {
        Comment comment = this.findByIdAndUserIdOrThrow(commentId, userId);
        comment.update(request);

        return CommentGetResponse.from(comment);
    }

    @Transactional
    public void delete(Long userId, Long commentId) {
        Comment comment = this.findByIdAndUserIdOrThrow(commentId, userId);
        commentRepository.delete(comment);
    }

    public void deleteAllByUserId(Long userId) {
        commentRepository.deleteAllByUserId(userId);
    }

    public void deleteAllByScheduleId(Long scheduleId) {
        commentRepository.deleteAllByScheduleId(scheduleId);
    }

    public long countByScheduleId(Long scheduleId) {
        return commentRepository.countByScheduleId(scheduleId);
    }

    private Comment findByIdAndUserIdOrThrow(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
