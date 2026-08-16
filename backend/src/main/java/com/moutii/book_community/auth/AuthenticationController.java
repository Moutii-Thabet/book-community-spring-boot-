package com.moutii.book_community.auth;

import com.moutii.book_community.auth.impl.AuthenticationServiceImpl;
import com.moutii.book_community.auth.request.*;
import com.moutii.book_community.auth.response.AuthenticationResponse;
import com.moutii.book_community.auth.response.ResetPasswordPermissionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @Valid
            @RequestBody
            final SignupRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid
            @RequestBody
            final LoginRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid
            @RequestBody
            final RefreshRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(request));
    }

    @PostMapping("/resetpw")
    public ResponseEntity<AuthenticationResponse> resetPassword(
            @RequestBody
            @Valid
            final ResetPasswordRequest request
            ) {
        return ResponseEntity.ok(this.authService.resetPassword(request));
    }

    @GetMapping("/reset/{resetToken}")
    public ResponseEntity<ResetPasswordPermissionResponse> resetPasswordPermission(
            @PathVariable String resetToken
    ) {
        final byte[] decodedBytes = Base64.getUrlDecoder().decode(resetToken);
        final String rawToken = new String(decodedBytes, StandardCharsets.UTF_8);
        return ResponseEntity.ok(this.authService.resetPasswordPermission(rawToken));
    }

    @PostMapping("/newpw")
    public ResponseEntity<AuthenticationResponse> newPassword(
            @RequestBody
            @Valid
            final NewPasswordRequest request
            ) {
        return ResponseEntity.ok(this.authService.newPassword(request));
    }

}
