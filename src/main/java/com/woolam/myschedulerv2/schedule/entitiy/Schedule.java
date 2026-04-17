package com.woolam.myschedulerv2.schedule.entitiy;

import com.woolam.myschedulerv2.common.entity.BaseEntity;
import com.woolam.myschedulerv2.schedule.dto.ScheduleCreateRequest;
import com.woolam.myschedulerv2.schedule.dto.ScheduleUpdateRequest;
import com.woolam.myschedulerv2.user.entitiy.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>일정 정보를 저장하는 도메인 엔티티 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    public static Schedule create(User user, ScheduleCreateRequest request) {
        Schedule schedule = new Schedule();
        schedule.user = user;
        schedule.title = request.getTitle();
        schedule.content = request.getContent();

        return schedule;
    }

    public void update(ScheduleUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
