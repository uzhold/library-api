package com.uzhold.library.controller;



import com.uzhold.library.dto.LoginRequest;
import com.uzhold.library.dto.LoginResponse;
import com.uzhold.library.dto.RegisterRequest;
import com.uzhold.library.dto.UserResponse;
import com.uzhold.library.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authservice;

    public AuthController(AuthService authservice) {
        this.authservice = authservice;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request){
        UserResponse response =  authservice.register(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse response = authservice.login(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
