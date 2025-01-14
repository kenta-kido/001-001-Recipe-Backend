package com.example.backend.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String email;
    private String role;
    private String extraInfo;
}
