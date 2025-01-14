package com.example.backend.controller;

import com.example.backend.dto.PasswordChangeRequestDTO;
import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;
import com.example.backend.entity.UserEntity;
import com.example.backend.service.UserService;
import com.example.backend.security.UserPrincipal; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/email")
    public ResponseEntity<?> updateUserInfo(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        // 自身の情報を取得
        UserEntity userEntity = userService.getUserById(currentUser.getUserId())
                                           .orElseThrow(() -> new RuntimeException("User not found"));

        // 更新可能なフィールドのみを更新
        if (userRequestDTO.getExtraInfo() != null) {
            userEntity.setExtraInfo(userRequestDTO.getExtraInfo());
        }
        if (userRequestDTO.getEmail() != null && !userRequestDTO.getEmail().isEmpty()) {
            userEntity.setEmail(userRequestDTO.getEmail());
        }

        // 更新処理
        UserEntity updatedUser = userService.saveUser(userEntity);

        // レスポンスDTOに変換して返す
        return ResponseEntity.ok(convertToResponseDTO(updatedUser));
    }

    private UserResponseDTO convertToResponseDTO(UserEntity user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setExtraInfo(user.getExtraInfo());
        return responseDTO;
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
    }

    // Principal からメールアドレスを取得
    String email = ((UserPrincipal) authentication.getPrincipal()).getEmail();

    // データベースからユーザーを取得
    UserEntity user = userService.findByEmail(email)
                                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // パスワードの一致を確認
    if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
        return ResponseEntity.badRequest().body("Current password is incorrect");
    }

    // パスワードを更新
    userService.updatePassword(user.getId(), passwordChangeRequest.getNewPassword());
    return ResponseEntity.ok().body("Password updated successfully");
}
    // データベース内のパスワードの確認エンドポイント
    @GetMapping("/db-password")
    public ResponseEntity<?> getDatabasePassword() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        // Principal から email を取得
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String email = userPrincipal.getUsername();

        // データベースから UserEntity を取得
        UserEntity userEntity = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok().body("Database Password: " + userEntity.getPassword());
    }
}
