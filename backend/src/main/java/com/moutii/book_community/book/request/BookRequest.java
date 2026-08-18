package com.moutii.book_community.book.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "A title must be provided")
    @Size(min = 3 , message = "Minimum size for a title is 3 characters")
    private String title;

    @NotBlank(message = "A title must be provided")
    @Size(min = 3 , message = "Minimum size for a title is 3 characters")
    private String description;

    private Integer rating;

    @NotNull
    private MultipartFile image;


}
