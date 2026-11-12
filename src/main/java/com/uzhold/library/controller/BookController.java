    package com.uzhold.library.controller;


    import com.uzhold.library.model.Book;
    import com.uzhold.library.service.BookService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;

    @RestController
    public class BookController {

        private final BookService bookService;

        public BookController(BookService bookService) {
            this.bookService = bookService;
        }


        @GetMapping("/books")
        public List<Book> getAllBooks(){
            return bookService.getAllBooks();
        }

        @PostMapping("/books")
        public ResponseEntity<Book> addBook(@RequestBody Book book){
            Book saved = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }

        @GetMapping("/books/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable Long id){
            Book book = bookService.getBookById(id);
            if(book != null){
                return ResponseEntity.ok(book);
            }
            return ResponseEntity.notFound().build();
        }

        @DeleteMapping("/books/{id}")
        public ResponseEntity<Void> deleteBookById(@PathVariable Long id){
            if(bookService.deleteBookById(id)){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }

        @PutMapping("/books/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody Book newData){
            Book updated = bookService.updateBook(id, newData);
            if(updated != null){
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        }
    }
