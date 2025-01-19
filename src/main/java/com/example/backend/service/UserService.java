package com.example.backend.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder を注入

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.findByEmail("kenta@kenta.com").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setEmail("kenta@kenta.com");
            admin.setPassword("$2a$12$OBnerD3ZrnkqY/ofkaxune1jnpUscFhTGCcuVA9x5lgAGAtr6Bss2"); // ハッシュ化されたパスワード
            admin.setRole("ROLE_ADMIN");
            admin.setExtraInfo("Admin user");
            userRepository.save(admin);
        }
        if (userRepository.findByEmail("user@kenta.com").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setEmail("user@kenta.com");
            user.setPassword("$2a$12$OBnerD3ZrnkqY/ofkaxune1jnpUscFhTGCcuVA9x5lgAGAtr6Bss2"); // ハッシュ化されたパスワード
            user.setRole("ROLE_USER");
            user.setExtraInfo("User");
            userRepository.save(user);
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity saveUser(UserEntity user) {
        // 既存のユーザーを取得
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // パスワードが変更されている場合のみハッシュ化
        if (!user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword()); // 既存のパスワードを保持
        }
    
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    
                    // パスワードが変更される場合のみハッシュ化
                    if (!updatedUser.getPassword().equals(user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    user.setRole(updatedUser.getRole());
                    user.setExtraInfo(updatedUser.getExtraInfo());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    
    public void updatePassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            // パスワードをハッシュ化して設定
            user.setPassword(passwordEncoder.encode(newPassword));
    
            // extraInfo の処理
            if (user.getExtraInfo() != null && user.getExtraInfo().contains("[Init Pass:")) {
                user.setExtraInfo(user.getExtraInfo().replaceAll("\\[Init Pass: .*?\\]", "").trim());
            }
    
            // パスワード更新メッセージを追記
            String updatedInfo = "Password updated by user";
            user.setExtraInfo(updatedInfo);
    
            // ユーザー情報を保存
            userRepository.save(user);
        });
    }
    

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
