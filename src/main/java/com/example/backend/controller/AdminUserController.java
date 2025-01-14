package com.example.backend.controller;

import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;
import com.example.backend.entity.UserEntity;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    // ユーザー一覧の取得
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return users.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    // IDでユーザーを取得
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(convertToResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ユーザーの作成
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserEntity userEntity = convertToEntity(userRequestDTO);
        UserEntity createdUser = userService.saveUser(userEntity);
        return ResponseEntity.ok(convertToResponseDTO(createdUser));
    }

    // ユーザーの編集
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserEntity userEntity = convertToEntity(userRequestDTO);
        UserEntity updatedUser = userService.updateUser(id, userEntity);
        return ResponseEntity.ok(convertToResponseDTO(updatedUser));
    }

    // ユーザーの削除
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Entity -> ResponseDTO の変換
    private UserResponseDTO convertToResponseDTO(UserEntity user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setExtraInfo(user.getExtraInfo());
        return responseDTO;
    }

    // RequestDTO -> Entity の変換
    private UserEntity convertToEntity(UserRequestDTO userRequestDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword()); // サービス層でハッシュ化
        user.setRole(userRequestDTO.getRole());
        user.setExtraInfo(userRequestDTO.getExtraInfo());
        return user;
    }
}
