package com.uzhold.library.service;


import com.uzhold.library.dto.RegisterRequest;
import com.uzhold.library.dto.UserResponse;
import com.uzhold.library.exception.UsernameAlreadyExistsException;
import com.uzhold.library.model.User;
import com.uzhold.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public UserResponse register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UsernameAlreadyExistsException(request.getUsername());
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername(), saved.getRole());
    }
}
