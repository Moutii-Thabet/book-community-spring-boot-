package com.moutii.book_community.auth.impl;

import com.moutii.book_community.auth.AuthenticationService;
import com.moutii.book_community.auth.EmailService;
import com.moutii.book_community.auth.request.*;
import com.moutii.book_community.auth.response.AuthenticationResponse;
import com.moutii.book_community.auth.response.ResetPasswordPermissionResponse;
import com.moutii.book_community.exception.BusinessException;
import com.moutii.book_community.exception.ErrorCode;
import com.moutii.book_community.security.JwtService;
import com.moutii.book_community.user.User;
import com.moutii.book_community.user.UserMapper;
import com.moutii.book_community.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

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
        System.out.println("bad credentials");
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

    @Override
    @Transactional
    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        if(!userRepo.existsByEmailIgnoreCase(request.getEmail())) {
            throw new UsernameNotFoundException("Username not found");
        }

        try {
            final String email = request.getEmail();
            final String rawToken = passwordEncoder.encode(email);
            assert rawToken != null;
            final String resetToken = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));

            final String url = "http://localhost:5173/auth/reset/" + resetToken;

            final String htmlBody = """
                    <p>You requested a password reset</p>
                    <p>Click this <a href='%s'>link</a>  to set a new password</p>
                    <p>This link expires in 10 minutes</p>
                    """.formatted(url);

            emailService.sendEmail(email,"Password Reset",htmlBody);

            User user = userRepo.findByEmailIgnoreCase(email).get();
            final LocalDateTime expiration = LocalDateTime.now().plusMinutes(10);
            user.setPwResetToken(rawToken);
            user.setPwResetTokenExpiration(expiration);
            userRepo.save(user);
            return AuthenticationResponse.builder()
                    .message("an email to reset your password will be sent soon")
                    .build();

        } catch (RuntimeException | MessagingException e) {
            throw new InternalException("There was a problem sending the email");
        }

    }

    @Override
    public ResetPasswordPermissionResponse resetPasswordPermission(String resetToken) {
        final User user = userRepo.findByPwResetToken(resetToken)
                .orElseThrow(()->new RuntimeException("Reset Token not found"));
        if(user.getPwResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset Token Expired");
        }

        return ResetPasswordPermissionResponse.builder()
                .message("Authorized to reset password")
                .resetToken(resetToken)
                .userId(user.getId())
                .build();
    }

    @Override
    public AuthenticationResponse newPassword(NewPasswordRequest request) {
        final User user = userRepo.findById(request.getUserId())
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,request.getUserId()));
        if(!user.getPwResetToken().equals(request.getToken()) ||
                user.getPwResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid reset Token");
        }

        final String encodedNewPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedNewPassword);
        user.setPwResetToken(null);
        user.setPwResetTokenExpiration(null);
        userRepo.save(user);
        return AuthenticationResponse.builder()
                .message("Password reset successfully")
                .build();
    }
}
