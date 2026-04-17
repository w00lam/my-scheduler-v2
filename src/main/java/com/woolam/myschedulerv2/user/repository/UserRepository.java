package com.woolam.myschedulerv2.user.repository;

import com.woolam.myschedulerv2.user.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <p>User엔티티에 대한 데이터베이스 액세스를 담당하는 리포지토리 인터페이스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByEmailIgnoreCase(String email);
}
