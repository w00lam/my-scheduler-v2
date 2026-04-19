package com.woolam.myschedulerv2.comment.entity;

import com.woolam.myschedulerv2.comment.dto.CommentCreateRequest;
import com.woolam.myschedulerv2.comment.dto.CommentUpdateRequest;
import com.woolam.myschedulerv2.common.entity.BaseEntity;
import com.woolam.myschedulerv2.schedule.entitiy.Schedule;
import com.woolam.myschedulerv2.user.entitiy.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>댓글 정보를 저장하는 도메인 엔티티 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-16
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(length = 30, nullable = false)
    private String content;

    public static Comment create(User user, Schedule schedule, CommentCreateRequest request) {
        Comment comment = new Comment();
        comment.user = user;
        comment.schedule = schedule;
        comment.content = request.getContent();

        return comment;
    }

    public void update(CommentUpdateRequest request) {
        this.content = request.getContent();
    }
}
