package com.uzhold.library.service;


import com.uzhold.library.dto.AuthorRequest;
import com.uzhold.library.exception.AuthorNotFoundException;
import com.uzhold.library.model.Author;
import com.uzhold.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repo;

    public AuthorService(AuthorRepository repo) {
        this.repo = repo;
    }

    public List<Author> getAuthors() {
        return repo.findAll();
    }


    public Author getAuthorById(Long id) {
        return repo.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author addAuthor(AuthorRequest request) {
        Author author= new Author();
        author.setName(request.getName());
        author.setCountry(request.getCountry());
        return repo.save(author);
    }

    public Author updateAuthor(AuthorRequest request, Long id) {
        Author author= getAuthorById(id);
        author.setName(request.getName());
        author.setCountry(request.getCountry());
        return repo.save(author);
    }

    public void deleteAuthorById(Long id) {
        repo.delete(getAuthorById(id));
    }
}
