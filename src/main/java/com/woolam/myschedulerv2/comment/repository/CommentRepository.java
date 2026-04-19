package com.woolam.myschedulerv2.comment.repository;

import com.woolam.myschedulerv2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>Comment 엔티티에 대한 데이터베이스 액세스를 담당하는 리포지토리 인터페이스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleId(Long scheduleId);

    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);

    long countByScheduleId(Long scheduleId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.schedule.id = :scheduleId")
    void deleteAllByScheduleId(@Param("scheduleId") Long scheduleId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
