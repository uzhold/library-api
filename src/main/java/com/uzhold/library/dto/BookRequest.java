package com.uzhold.library.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String author;
}
