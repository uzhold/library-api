package com.uzhold.library.controller;



import com.uzhold.library.dto.AuthorRequest;
import com.uzhold.library.model.Author;
import com.uzhold.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {


    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAuthors(){
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }
    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody @Valid AuthorRequest request){
        Author saved = authorService.addAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@RequestBody @Valid AuthorRequest request,
                                                @PathVariable Long id){
        Author saved = authorService.updateAuthor(request,id);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.noContent().build();
    }
}
