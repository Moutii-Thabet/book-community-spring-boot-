package com.moutii.book_community.auth.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @Email(message = "You have to enter a valid email")
    @NotBlank(message = "You must enter an email")
    private String email;

    @NotBlank(message = "You must enter a password")
    @Size(min=8,message = "password minimum size is 8")
    private String password;
}
