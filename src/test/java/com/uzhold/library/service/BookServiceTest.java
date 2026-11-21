package com.uzhold.library.service;


import com.uzhold.library.dto.BookRequest;
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
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;


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
        List<Book> result = bookService.getBooksByAuthorId(1L);
        assertEquals(2, result.size());
        assertEquals("O'tkan kunlar", result.get(0).getTitle());
    }

    @Test
    public void shouldThrowWhenAddingBookWithMissingAuthor() {
        BookRequest request = new BookRequest();
        request.setTitle("Test");
        request.setAuthorId(99L);
        when(authorService.getAuthorById(99L)).thenThrow(AuthorNotFoundException.class);
        assertThrows(AuthorNotFoundException.class, () -> bookService.addBook(request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    public void shouldSaveBookWhenAuthorFound() {
        BookRequest request = new BookRequest();
        request.setAuthorId(1L);
        request.setTitle("test");
        Author author = new Author();
        when(authorService.getAuthorById(1L)).thenReturn(author);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("test");

        when(bookRepository.save(any())).thenReturn(savedBook);

        Book result = bookService.addBook(request);

        assertEquals(1L, result.getId());
        assertEquals("test", result.getTitle());
        verify(bookRepository).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFoundUpdating(){
        BookRequest request = new BookRequest();
        request.setTitle("test");
        request.setAuthorId(1L);

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class ,()->bookService.updateBook(99L, request));
        verify(bookRepository, never()).save(any());

    }
    @Test
    public void shouldThrowWhenUpdatingWithMissingAuthor(){
        Book book = new Book();
        book.setId(1L);
        book.setTitle("test");
        BookRequest request = new BookRequest();
        request.setAuthorId(99L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorService.getAuthorById(99L)).thenThrow(AuthorNotFoundException.class);

        assertThrows(AuthorNotFoundException.class , ()->bookService.updateBook(1L, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    public void shouldReturnBookWhenBookAndAuthorFound(){
        Author author = new Author();
        author.setName("Xoshim");
        author.setCountry("Uzb");
        author.setId(2L);

        Book book = new Book();
        book.setTitle("test");
        book.setId(1L);

        BookRequest request = new BookRequest();
        request.setAuthorId(2L);
        request.setTitle("NewTitle");

        when(authorService.getAuthorById(2L)).thenReturn(author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.updateBook(1L , request);

        assertEquals("NewTitle", result.getTitle());
        assertEquals("Xoshim", result.getAuthor().getName());
        verify(bookRepository).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenDeletedBookNotFound(){
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class , () -> bookService.deleteBookById(99L));
        verify(bookRepository, never()).delete(any());
    }

    @Test
    public void shouldDeleteBookWhenFound(){
        Book book = new Book();
        book.setTitle("test");
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.deleteBookById(1L);

        verify(bookRepository).delete(book);
    }
}
