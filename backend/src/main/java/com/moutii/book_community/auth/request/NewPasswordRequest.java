package com.moutii.book_community.auth.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPasswordRequest {

    @NotBlank(message = "You must enter a password")
    @Size(min=8,message = "password minimum size is 8")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$")
    private String password;

    @NotBlank(message = "A user Id must be provided")
    private String userId;

    @NotBlank(message = "A user Id must be provided")
    private String token;

}
