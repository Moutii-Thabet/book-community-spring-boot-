package com.moutii.book_community.book.response;


import com.moutii.book_community.book.Book;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleBookResponse {

    private String message;

    private Book book;

}
