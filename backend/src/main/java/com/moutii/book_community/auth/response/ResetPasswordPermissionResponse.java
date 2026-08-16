package com.moutii.book_community.auth.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordPermissionResponse {

    private String resetToken;

    private String userId;

    private String message;

}
