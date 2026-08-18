package com.moutii.book_community.book.impl;

import com.moutii.book_community.book.Book;
import com.moutii.book_community.book.BookMapper;
import com.moutii.book_community.book.BookRepository;
import com.moutii.book_community.book.BookService;
import com.moutii.book_community.book.request.BookRequest;
import com.moutii.book_community.book.response.MultipleBookResponse;
import com.moutii.book_community.book.response.SimpleMessageResponse;
import com.moutii.book_community.book.response.SingleBookResponse;
import com.moutii.book_community.exception.BusinessException;
import com.moutii.book_community.exception.ErrorCode;
import com.moutii.book_community.user.User;
import com.moutii.book_community.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    private final UserRepository userRepo;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public MultipleBookResponse getBooks() {
        return MultipleBookResponse.builder()
                .message("Fetched Books Succesfully")
                .books(bookRepo.findAll(Sort.by("createdAt").descending()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SingleBookResponse getBook(String bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()->new BusinessException(ErrorCode.BOOK_NOT_FOUND));

        return SingleBookResponse.<Book>builder()
                .message("fetched book successfully")
                .book(book)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MultipleBookResponse getUserBooks(String userId) {
        if(!userRepo.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return MultipleBookResponse.builder()
                .message("Fetched your books successfully")
                .books(bookRepo.findByCreatedBy(userId))
                .build();
    }

    @Override

    public SimpleMessageResponse addBook(BookRequest request, String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));

        Book book = bookMapper.toBook(request);
        book.setCreator(user);
        bookRepo.save(book);
        return SimpleMessageResponse.builder()
                .message("book added successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleMessageResponse patchBook(BookRequest request, String bookId, String userId) {
        if(!userRepo.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()->new BusinessException(ErrorCode.BOOK_NOT_FOUND));

        if(!book.getCreatedBy().equals(userId)) {
            throw new BusinessException(ErrorCode.USER_MISMATCH);
        }
        bookMapper.updateBook(book,request);
        bookRepo.save(book);

        return SimpleMessageResponse.builder()
                .message("Book updated successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleMessageResponse deleteBook(String bookId, String userId) {
        if(!userRepo.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()->new BusinessException(ErrorCode.BOOK_NOT_FOUND));

        if(!book.getCreatedBy().equals(userId)) {
            throw new BusinessException(ErrorCode.USER_MISMATCH);
        }
        bookRepo.delete(book);
        return SimpleMessageResponse.builder()
                .message("Book deleted successfully")
                .build();
    }
}
