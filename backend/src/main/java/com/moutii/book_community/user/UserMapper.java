package com.moutii.book_community.user;

import com.moutii.book_community.auth.request.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(SignupRequest request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .build();
    }

}
