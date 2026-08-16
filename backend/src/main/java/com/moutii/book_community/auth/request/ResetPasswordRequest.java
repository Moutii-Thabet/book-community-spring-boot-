package com.moutii.book_community.auth.request;


import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @Email(message="You must enter a valid email")
    private String email;
}
