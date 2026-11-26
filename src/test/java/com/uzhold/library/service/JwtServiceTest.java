package com.uzhold.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.JwtException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {


    private static final String TEST_SECRET = "2P3qE9YDcesn52bvXwYx33Iks9+OU5JGEydHYvFdWXY=";

    @Test
    void shouldGenerateTokenWithThreeParts() {
        // Arrange
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);

        // Act
        String token = jwtService.generateToken("admin2", "ADMIN");

        // Assert
        System.out.println(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void shouldExtractUsernameFromToken() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        String token = jwtService.generateToken("admin2", "ADMIN");

        String username = jwtService.extractUsername(token);

        assertEquals("admin2", username);
    }

    @Test
    void shouldThrowWhenTokenIsTampered() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        String token = jwtService.generateToken("admin2", "ADMIN");

        String hackerToken = jwtService.generateToken("hacker", "ADMIN");


        String[] parts = token.split("\\.");
        String hackerPayload = hackerToken.split("\\.")[1];
        String tampered = parts[0] + "." + hackerPayload + "." + parts[2];

        assertThrows(JwtException.class, () -> jwtService.extractUsername(tampered));
    }
}
