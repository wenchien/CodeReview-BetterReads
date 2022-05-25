package io.jdevelop.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jdevelop.beans.Book;
import io.jdevelop.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    public Optional<Book> findById(String bookId) {
        return bookRepo.findById(bookId);
    }


}
