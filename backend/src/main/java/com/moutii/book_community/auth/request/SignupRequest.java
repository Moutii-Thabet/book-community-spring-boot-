package com.moutii.book_community.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @Email(message = "You have to enter a valid email")
    @NotBlank(message = "You must enter an email")
    private String email;

    @NotBlank(message = "You must enter a name")
    @Size(max = 20, min=2,message = "name must have a size between 2 and 20 characters")
    private String name;

    @NotBlank(message = "You must enter a password")
    @Size(min=8,message = "password minimum size is 8")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$")
    private String password;

    @NotBlank(message = "You must enter a password")
    @Size(min=8,message = "password minimum size is 8")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$")
    private String confirmPassword;

}
