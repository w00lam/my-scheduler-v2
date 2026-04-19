package com.woolam.myschedulerv2.user.service;

import com.woolam.myschedulerv2.common.exception.ErrorCode;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.config.PasswordEncoder;
import com.woolam.myschedulerv2.user.dto.*;
import com.woolam.myschedulerv2.user.entitiy.User;
import com.woolam.myschedulerv2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>유저 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 유저의 생성, 조회, 변경, 삭제 기능을 제공합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResponse signUp(UserCreateRequest request) {
        validateEmailDuplicate(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.create(request, encodedPassword);
        userRepository.save(user);

        return UserCreateResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserGetResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserGetResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserGetResponse getMyInfo(Long userId) {
        User user = this.findUserOrThrow(userId);

        return UserGetResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {
        User user = this.findUserOrThrow(userId);
        user.update(request);

        return UserUpdateResponse.from(user);
    }

    @Transactional
    public void delete(Long userId) {
        User user = this.findUserOrThrow(userId);
        userRepository.delete(user);
    }

    public User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateEmailDuplicate(UserCreateRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ServiceException(ErrorCode.EMAIL_DUPLICATE);
        }
    }
}
