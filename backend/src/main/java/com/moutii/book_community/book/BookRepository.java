package com.moutii.book_community.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,String>{
   List<Book> findByCreatedBy(String creatorId);
}
