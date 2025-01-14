package com.example.backend.controller;

import com.example.backend.dto.PasswordChangeRequestDTO;
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
    // @GetMapping("/info")
    // public ResponseEntity<?> getUserInfo() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         return ResponseEntity.status(401).body("User is not authenticated");
    //     }

    //     // Principal を UserPrincipal 型にキャスト
    //     UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    //     return ResponseEntity.ok().body("Authenticated user: " + userPrincipal.getEmail());
    // }
  
    // @GetMapping("/auth-info")
    // public ResponseEntity<?> getAuthInfo() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         return ResponseEntity.status(401).body("User is not authenticated");
    //     }
    
    //     // Principal を確認
    //     Object principal = authentication.getPrincipal();
    
    //     // UserPrincipal にキャスト
    //     if (principal instanceof UserPrincipal) {
    //         UserPrincipal userPrincipal = (UserPrincipal) principal;
    //         return ResponseEntity.ok().body("Authenticated Email: " + userPrincipal.getUsername() +
    //                                         ", Password: " + userPrincipal.getPassword());
    //     } else {
    //         return ResponseEntity.badRequest().body("Principal is not of type UserPrincipal");
    //     }
    // }

    // @GetMapping("/auth-info2")
    // public ResponseEntity<?> getAuthInfo2() {
    //     // SecurityContext から Authentication を取得
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         return ResponseEntity.status(401).body("User is not authenticated");
    //     }

    //     // Principal を確認
    //     Object principal = authentication.getPrincipal();

    //     if (principal instanceof UserPrincipal) {
    //         UserPrincipal userPrincipal = (UserPrincipal) principal;
    //         return ResponseEntity.ok().body("Authenticated Email: " + userPrincipal.getEmail() +
    //                 ", Password: " + userPrincipal.getPassword() +
    //                 ", User ID: " + userPrincipal.getUserId());
    //     }

    //     return ResponseEntity.ok().body("Principal is not of type UserPrincipal. Actual type: " + principal.getClass().getName());
    // }

    // @GetMapping("/auth-info3")
    // public ResponseEntity<?> getAuthInfo3() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         return ResponseEntity.status(401).body("User is not authenticated");
    //     }

    //     // Principal を確認
    //     Object principal = authentication.getPrincipal();
    //     System.out.println("Principal Class: " + principal.getClass().getName());
    //     System.out.println("Principal: " + principal);

    //     if (principal instanceof UserPrincipal) {
    //         UserPrincipal userPrincipal = (UserPrincipal) principal;
    //         System.out.println("UserPrincipal - Email: " + userPrincipal.getUsername());
    //         System.out.println("UserPrincipal - Password: " + userPrincipal.getPassword());
    //         return ResponseEntity.ok().body("Authenticated Email: " + userPrincipal.getUsername() +
    //                                         ", Password: " + userPrincipal.getPassword());
    //     } else {
    //         return ResponseEntity.badRequest().body("Principal is not of type UserPrincipal");
    //     }
    // }
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
