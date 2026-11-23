package com.uzhold.library.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void shouldShowBcryptHashes(){
        String hash1 = encoder.encode("qwerty123");
        String hash2 = encoder.encode("qwerty123");


        assertNotEquals(hash1,hash2);
        assertTrue(encoder.matches("qwerty123",hash1));
        assertFalse(encoder.matches("qwerty124", hash2));
    }

}
