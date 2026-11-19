package com.uzhold.library.service;


import com.uzhold.library.exception.BookNotFoundException;
import com.uzhold.library.model.Book;
import com.uzhold.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void shouldReturnBookWhenFound(){
        Book book = new Book();
        book.setTitle("Mulla");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals("Mulla", result.getTitle());
    }
    @Test
    public void shouldThrowExceptionWhenBookNotFound(){
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(99L));
    }
}
