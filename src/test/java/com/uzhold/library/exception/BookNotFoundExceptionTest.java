
package com.uzhold.library.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookNotFoundExceptionTest {

    @Test
    void shouldCreateMessageWithId() {
        // Arrange
        Long id = 5L;
        // Act
        BookNotFoundException ex = new BookNotFoundException(id);
        // Assert
        assertEquals("Book not found with id: 5", ex.getMessage());
    }
}