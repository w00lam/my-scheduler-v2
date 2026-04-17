package com.woolam.myschedulerv2.user.entitiy;

import com.woolam.myschedulerv2.common.entity.BaseEntity;
import com.woolam.myschedulerv2.user.dto.UserCreateRequest;
import com.woolam.myschedulerv2.user.dto.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>유저 정보를 저장하는 도메인 엔티티 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    public static User create(UserCreateRequest request) {
        User user = new User();
        user.name = request.getName();
        user.email = request.getEmail();
        user.password = request.getPassword();

        return user;
    }

    public void update(UserUpdateRequest request) {
        this.name = request.getName();
    }
}
