package com.woolam.myschedulerv2.schedule.repository;

import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>Schedule 엔티티에 대한 데이터베이스 액세스를 담당하는 리포지토리 인터페이스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE :userId IS NULL OR s.user.id = :userId")
    List<Schedule> findSchedulesOrByUserId(@Param("userId") Long userId);

    Optional<Schedule> findByIdAndUserId(Long scheduleId, Long userId);
}
