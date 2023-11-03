package com.epam.testProject.repository;

import com.epam.testProject.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {

  Book findById(String id);
}
