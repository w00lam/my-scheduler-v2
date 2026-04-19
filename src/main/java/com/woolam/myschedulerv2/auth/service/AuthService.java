package com.woolam.myschedulerv2.auth.service;

import com.woolam.myschedulerv2.auth.dto.LoginRequest;
import com.woolam.myschedulerv2.auth.dto.LoginUserDto;
import com.woolam.myschedulerv2.common.exception.ErrorCode;
import com.woolam.myschedulerv2.common.exception.ServiceException;
import com.woolam.myschedulerv2.config.PasswordEncoder;
import com.woolam.myschedulerv2.user.entitiy.User;
import com.woolam.myschedulerv2.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>인증/인가 비즈니스 로직을 처리하는 서비스 클래스입니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(LoginRequest request, HttpSession session) {
        User user = findUserByEmailOrThrow(request);
        validatePassWord(user, request);


        session.setAttribute("loginUser", LoginUserDto.from(user));
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    private User findUserByEmailOrThrow(LoginRequest request) {
        return userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));
    }

    private void validatePassWord(User user, LoginRequest request) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
