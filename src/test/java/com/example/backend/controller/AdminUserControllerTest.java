package com.example.backend.controller;

import static org.mockito.Mockito.*;  // For mocking behavior in tests
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  // For building HTTP requests in MockMvc
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  // For matching response status and content

import org.junit.jupiter.api.BeforeEach;  // JUnit 5 BeforeEach annotation to run setup before each test
import org.junit.jupiter.api.Test;  // JUnit 5 Test annotation to mark test methods
import org.junit.jupiter.api.extension.ExtendWith;  // JUnit 5 extension to integrate Spring with JUnit
import org.springframework.beans.factory.annotation.Autowired;  // Spring annotation to inject dependencies
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;  // Automatically configure MockMvc for testing
import org.springframework.boot.test.context.SpringBootTest;  // Spring Boot test annotation to load the application context
import org.springframework.boot.test.mock.mockito.MockBean;  // For mocking beans in the Spring context
import org.springframework.http.HttpStatus;  // HTTP status codes
import org.springframework.security.test.context.support.WithMockUser;  // Mock a user for security context
import org.springframework.test.web.servlet.MockMvc;  // MockMvc class to simulate HTTP requests
import org.springframework.test.context.junit.jupiter.SpringExtension;  // JUnit 5 integration for Spring
import com.example.backend.entity.UserEntity;  // User entity class
import com.example.backend.service.UserService;  // Service class to be mocked
import com.example.backend.dto.UserResponseDTO;  // DTO for user response
import java.util.Optional;  // Optional for handling null values
import java.util.Collections;  // For working with collections like lists

@ExtendWith(SpringExtension.class)  // Integrates Spring with JUnit 5 for testing
@SpringBootTest  // Starts the full Spring context for the test
@AutoConfigureMockMvc  // Automatically configures MockMvc for performing HTTP requests in tests
public class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc instance to simulate HTTP requests in the tests

    @MockBean
    private UserService userService;  // Mock the UserService to simulate service layer behavior

    private UserEntity admin;  // Declare the admin user entity for use in the tests

    // Setup method: Executes before each test
    @BeforeEach
    public void setUp() {
        admin = new UserEntity();  // Create a new admin user entity
        admin.setId(1L);  // Set the ID for the admin user
        admin.setEmail("admin@test.com");  // Set the email for the admin user
        admin.setRole("ADMIN");  // Set the role for the admin user

        // Mock the behavior of the service layer
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(admin));  // Return the admin user in a list when getAllUsers is called
        when(userService.getUserById(1L)).thenReturn(Optional.of(admin));  // Return the admin user when getUserById is called with the ID 1
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")  // Mock a user with ADMIN role for testing secured endpoints
    public void testGetAllUsers() throws Exception {
        // Perform a GET request to the /admin/users endpoint and verify the response
        mockMvc.perform(get("/admin/users"))  // Perform a GET request to the specified URL
                .andExpect(status().isOk())  // Expect an HTTP status of 200 OK in the response
                .andExpect(jsonPath("$[0].email").value("admin@test.com"));  // Verify the email in the response body matches the expected value
    }
}
