package com.uzhold.library.service;


import com.uzhold.library.dto.AuthorRequest;

import com.uzhold.library.exception.AuthorNotFoundException;

import com.uzhold.library.model.Author;
import com.uzhold.library.repository.AuthorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService;


    @Test
    public void shouldThrowExceptionWhenIdNotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(99L));
    }

    @Test
    public void shouldReturnAuthorWhenFound() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Shoh");
        author.setCountry("Uzbekistan");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        assertEquals(1L, authorService.getAuthorById(1L).getId());
        assertEquals("Shoh", authorService.getAuthorById(1L).getName());
    }

    @Test
    public void shouldSaveAndReturnAuthor() {
        AuthorRequest request = new AuthorRequest();
        request.setCountry("Uzb");
        request.setName("Anvar");

        Author author = new Author();
        author.setCountry("Uzb");
        author.setName("Anvar");
        author.setId(1L);

        when(authorRepository.save(any())).thenReturn(author);

        Author author1 = authorService.addAuthor(request);

        assertEquals(1L, author1.getId());
        verify(authorRepository).save(any());

    }

    @Test
    public void shouldThrowExceptionUpdatingAuthorNotFound() {

        AuthorRequest request = new AuthorRequest();
        request.setName("Shoh");
        request.setCountry("Uzb");

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(request, 99L));
        verify(authorRepository , never()).save(any());
    }

    @Test
    public void shouldReturnUpdatedAuthorWhenAuthorFound() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Shoh");
        author.setCountry("Uzbekistan");

        AuthorRequest request = new AuthorRequest();
        request.setCountry("Uzb");
        request.setName("Anvar");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(author);

        Author updated = authorService.updateAuthor(request, 1L);
        assertEquals("Anvar", updated.getName());
        verify(authorRepository).save(any());
    }
    @Test
    public void shouldThrowWhenDeletingMissingAuthor(){
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, ()-> authorService.deleteAuthorById(99L));
        verify(authorRepository,never()).delete(any());
    }
    @Test
    public void shouldDeleteAuthorWhenAuthorFound(){
        Author author = new Author();
        author.setCountry("Uzbekistan");
        author.setName("Shoh");
        author.setId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        authorService.deleteAuthorById(1L);

        verify(authorRepository).delete(author);
    }

}
