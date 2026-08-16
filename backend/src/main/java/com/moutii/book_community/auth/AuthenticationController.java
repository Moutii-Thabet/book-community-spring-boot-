package com.moutii.book_community.auth;

import com.moutii.book_community.auth.impl.AuthenticationServiceImpl;
import com.moutii.book_community.auth.request.LoginRequest;
import com.moutii.book_community.auth.request.RefreshRequest;
import com.moutii.book_community.auth.request.SignupRequest;
import com.moutii.book_community.auth.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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

}
