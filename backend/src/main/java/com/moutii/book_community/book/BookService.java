package com.moutii.book_community.book;

import com.moutii.book_community.book.request.BookRequest;
import com.moutii.book_community.book.response.MultipleBookResponse;
import com.moutii.book_community.book.response.SimpleMessageResponse;
import com.moutii.book_community.book.response.SingleBookResponse;

public interface BookService {

    MultipleBookResponse getBooks();

    SingleBookResponse getBook(String bookId);

    MultipleBookResponse getUserBooks(String userId);

    SimpleMessageResponse addBook(BookRequest book, String userId);

    SimpleMessageResponse patchBook(BookRequest book,String bookId, String userId);

    SimpleMessageResponse deleteBook(String bookId, String userId);



}
