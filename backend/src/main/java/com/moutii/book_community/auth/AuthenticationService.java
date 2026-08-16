package com.moutii.book_community.auth;

import com.moutii.book_community.auth.request.*;
import com.moutii.book_community.auth.response.AuthenticationResponse;
import com.moutii.book_community.auth.response.ResetPasswordPermissionResponse;

public interface AuthenticationService {

     AuthenticationResponse signup(SignupRequest request);

    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);

    AuthenticationResponse resetPassword(ResetPasswordRequest request);

    ResetPasswordPermissionResponse resetPasswordPermission(String resetToken);

    AuthenticationResponse newPassword(NewPasswordRequest request);






}
