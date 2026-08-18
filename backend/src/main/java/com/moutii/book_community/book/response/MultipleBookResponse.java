package com.moutii.book_community.book.response;


import com.moutii.book_community.book.Book;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultipleBookResponse {

    private String message;

    private List<Book> books;

}
