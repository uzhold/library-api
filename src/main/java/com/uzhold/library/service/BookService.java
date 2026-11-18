package com.uzhold.library.service;

import com.uzhold.library.dto.BookRequest;
import com.uzhold.library.exception.BookNotFoundException;
import com.uzhold.library.model.Author;
import com.uzhold.library.model.Book;
import com.uzhold.library.repository.BookRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(BookRequest request) {
        Book book = new Book();
        Author author = authorService.getAuthorById(request.getAuthorId());
        book.setAuthor(author);
        book.setTitle(request.getTitle());
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteBookById(Long id) {
        bookRepository.delete(getBookById(id));

    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = getBookById(id);
        book.setTitle(request.getTitle());
        Author author = authorService.getAuthorById(request.getAuthorId());
        book.setAuthor(author);
        return bookRepository.save(book);
    }
}
