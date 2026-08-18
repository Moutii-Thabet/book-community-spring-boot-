package com.moutii.book_community.book;

import com.moutii.book_community.book.request.BookRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {

        final String imageName = request.getImage().getOriginalFilename();
        final String imageType = request.getImage().getContentType();
        final byte[] imageData;
        try {
            imageData = request.getImage().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Book.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageName(imageName)
                .imageType(imageType)
                .imageData(imageData)
                .rating(request.getRating() == null ? 99 : request.getRating())
                .build();
    }

    public Book updateBook(Book updatedBook, BookRequest request) {
        if(!updatedBook.getTitle().equals(request.getTitle())) {
            updatedBook.setTitle(request.getTitle());
        }
        if(!updatedBook.getDescription().equals(request.getDescription())) {
            updatedBook.setDescription(request.getDescription());
        }
        if(!updatedBook.getRating().equals(request.getRating())) {
            updatedBook.setRating(request.getRating());
        }

        final String imageName = request.getImage().getOriginalFilename();
        final String imageType = request.getImage().getContentType();
        final byte[] imageData;
        try {
            imageData = request.getImage().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updatedBook.setImageName(imageName);
        updatedBook.setImageType(imageType);
        updatedBook.setImageData(imageData);

        return updatedBook;
    }

}
