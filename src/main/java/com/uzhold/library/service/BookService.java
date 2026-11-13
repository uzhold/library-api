package com.uzhold.library.service;

import com.uzhold.library.dto.BookRequest;
import com.uzhold.library.model.Book;
import com.uzhold.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(BookRequest request) {
        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setTitle(request.getTitle());
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public boolean deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        return bookRepository.save(book);
    }
}
