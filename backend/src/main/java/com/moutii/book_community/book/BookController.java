package com.moutii.book_community.book;

import com.moutii.book_community.book.request.BookRequest;
import com.moutii.book_community.book.response.MultipleBookResponse;
import com.moutii.book_community.book.response.SimpleMessageResponse;
import com.moutii.book_community.book.response.SingleBookResponse;
import com.moutii.book_community.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/books")
    public ResponseEntity<MultipleBookResponse> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<SingleBookResponse> getBooks(
            @PathVariable String bookId
    ) {
        return ResponseEntity.ok(service.getBook(bookId));
    }

    @GetMapping("/admin/books")
    public ResponseEntity<MultipleBookResponse> getUserBooks(Authentication principle) {
        return ResponseEntity.ok(service.getUserBooks(getId(principle)));
    }

    @PostMapping(value = "/admin/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> addBook(
            @Valid
            @ModelAttribute final BookRequest request,
            Authentication principle
            ) {
        return ResponseEntity.ok(service.addBook(request,getId(principle)));
    }

    @PatchMapping(value = "/admin/book/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> patchBook(
            @Valid
            @ModelAttribute final BookRequest request,
            @PathVariable final String bookId,
            Authentication principle
    ) {
        return ResponseEntity.ok(service.patchBook(request,bookId,getId(principle)));
    }

    @DeleteMapping("/admin/book/{bookId}")
    public ResponseEntity<SimpleMessageResponse> deleteBook(
            @PathVariable final String bookId,
            Authentication principle
    ) {
        return ResponseEntity.ok(service.deleteBook(bookId,getId(principle)));
    }

    private static String getId(Authentication principle) {
        return ((User) Objects.requireNonNull(principle.getPrincipal())).get_id();
    }





}
