package com.moutii.book_community.auth.impl;

import com.moutii.book_community.auth.AuthenticationService;
import com.moutii.book_community.auth.request.LoginRequest;
import com.moutii.book_community.auth.request.RefreshRequest;
import com.moutii.book_community.auth.request.SignupRequest;
import com.moutii.book_community.auth.response.AuthenticationResponse;
import com.moutii.book_community.exception.BusinessException;
import com.moutii.book_community.exception.ErrorCode;
import com.moutii.book_community.security.JwtService;
import com.moutii.book_community.user.User;
import com.moutii.book_community.user.UserMapper;
import com.moutii.book_community.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;


    @Override
    @Transactional
    public AuthenticationResponse signup(SignupRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }
        if(userRepo.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        final User user = userMapper.toUser(request);
        user.setBooks(new ArrayList<>());
        userRepo.save(user);
        return AuthenticationResponse.builder()
                .message("User created successfully")
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        final User user = (User) auth.getPrincipal();
        assert user != null;
        final String accessToken = jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .message("Logged in successfully")
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {

        final String accessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .message("Access token refreshed successfully")
                .build();
    }
}
