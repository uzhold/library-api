package com.uzhold.library.service;


import com.uzhold.library.exception.AuthorNotFoundException;
import com.uzhold.library.exception.BookNotFoundException;
import com.uzhold.library.model.Author;
import com.uzhold.library.model.Book;
import com.uzhold.library.repository.BookRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorService authorService;

    @InjectMocks
    BookService bookService;

    @Test
    public void shouldReturnBookWhenFound() {
        Book book = new Book();
        book.setTitle("Mulla");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals("Mulla", result.getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(99L));
    }

    @Test
    public void shouldThrowExceptionWhenAuthorNotFound() {
        when(authorService.getAuthorById(99L)).thenThrow(new AuthorNotFoundException(99L));
        assertThrows(AuthorNotFoundException.class, () -> bookService.getBooksByAuthorId(99L));
    }

    @Test
    public void shouldReturnBooksWhenAuthorFound() {
        Author author1 = new Author();

        Book book1 = new Book();
        book1.setTitle("O'tkan kunlar");
        Book book2 = new Book();
        book2.setTitle("Mehrobdan chayon");
        List<Book> list = List.of(book1, book2);
        when(authorService.getAuthorById(1L)).thenReturn(author1);
        when(bookRepository.findByAuthorId(1L)).thenReturn(list);
        List <Book> result = bookService.getBooksByAuthorId(1L);
        assertEquals(2, result.size());
        assertEquals("O'tkan kunlar", result.get(0).getTitle());    }
}
