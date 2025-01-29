package com.example.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  // GET request builder
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  // Status code validation
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;  // Response content validation

import java.util.Collections;

import org.junit.jupiter.api.Test;  // JUnit 5 Test annotation
import org.junit.jupiter.api.extension.ExtendWith;  // SpringExtension annotation to integrate with JUnit 5
import org.springframework.beans.factory.annotation.Value;  // Inject properties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;  // Auto-configuration for MockMvc
import org.springframework.boot.test.context.SpringBootTest;  // To start the Spring Boot test context
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;  // MockMvc class for simulating HTTP requests
import org.springframework.beans.factory.annotation.Autowired;  // For dependency injection from Spring container
import org.springframework.test.context.junit.jupiter.SpringExtension;  // SpringExtension for JUnit 5 integration
import com.example.backend.security.UserPrincipal;

@ExtendWith(SpringExtension.class)  // Use SpringExtension to support Spring context in JUnit 5
@SpringBootTest  // Starts the Spring Boot test context
@AutoConfigureMockMvc  // Automatically configures MockMvc to simulate HTTP requests
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc instance for making HTTP requests in tests

    @MockBean
    private UserPrincipal userPrincipal;  // Mock the UserPrincipal to inject as the currently authenticated user

    @Value("${test.default.password}")  // Inject the value of test.default.password from application properties
    private String testDefaultPassword;  // Used in the test to check the response content

    /**
     * Test for the "/greeting" endpoint.
     * This test performs a GET request and checks if the response status is OK (200)
     * and if the response content is as expected.
     */
    @Test
    public void testGreeting() throws Exception {
        // Perform a GET request to the "/greeting" endpoint
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())  // Expect an HTTP status of 200 OK
                .andExpect(content().string("Hello test, Heroku Auto Deploy, test env: " + testDefaultPassword));  // Expect the response content to match the expected string
    }

    /**
     * Test for the "/secured" endpoint, which requires a user to be authenticated.
     * This test mocks the UserPrincipal object and manually sets it in the SecurityContext.
     * It then verifies the response to ensure that the user is correctly authenticated and the expected content is returned.
     */
    @Test
    public void testSecuredEndpoint() throws Exception {
        // Mock the UserPrincipal's methods to return a mocked user
        when(userPrincipal.getEmail()).thenReturn("test@test.com");
        when(userPrincipal.getUserId()).thenReturn(1L);

        // Create a SimpleGrantedAuthority for the "USER" role
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        // Manually set the mocked UserPrincipal into the SecurityContext with the "USER" role
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userPrincipal, null, Collections.singletonList(authority))
        );

        // Perform a GET request to the "/secured" endpoint
        mockMvc.perform(get("/secured"))
                .andExpect(status().isOk())  // Expect an HTTP status of 200 OK
                .andExpect(content().string("if you see this, then you are logged in as user test@test.com / User ID: 1"));  // Validate response content
    }


    @Test
    public void testAdminEndpoint() throws Exception {
        // Mock the UserPrincipal's methods to return a mocked user
        when(userPrincipal.getEmail()).thenReturn("admin@test.com");
        when(userPrincipal.getUserId()).thenReturn(1L);

        // Create a SimpleGrantedAuthority for the "USER" role
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");

        // Manually set the mocked UserPrincipal into the SecurityContext with the "USER" role
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userPrincipal, null, Collections.singletonList(authority))
        );

        // Perform a GET request to the "/secured" endpoint
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())  // Expect an HTTP status of 200 OK
                .andExpect(content().string("if you see this, then you are ADMIN. User ID: 1"));  // Validate response content
    }


    

}
