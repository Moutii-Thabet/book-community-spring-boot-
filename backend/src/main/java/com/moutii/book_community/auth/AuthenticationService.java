package com.moutii.book_community.auth;

import com.moutii.book_community.auth.request.LoginRequest;
import com.moutii.book_community.auth.request.RefreshRequest;
import com.moutii.book_community.auth.request.SignupRequest;
import com.moutii.book_community.auth.response.AuthenticationResponse;

public interface AuthenticationService {

     AuthenticationResponse signup(SignupRequest request);

    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);


}
