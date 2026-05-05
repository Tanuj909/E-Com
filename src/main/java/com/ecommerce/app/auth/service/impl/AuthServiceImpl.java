package com.ecommerce.app.auth.service.impl;

import com.ecommerce.app.auth.dto.RegisterRequest;
import com.ecommerce.app.auth.dto.AuthResponse;
import com.ecommerce.app.auth.entity.Role;
import com.ecommerce.app.auth.entity.User;
import com.ecommerce.app.auth.entity.UserRole;
import com.ecommerce.app.auth.repository.RoleRepository;
import com.ecommerce.app.auth.repository.UserRepository;
import com.ecommerce.app.auth.repository.UserRoleRepository;
import com.ecommerce.app.auth.service.AuthService;
import com.ecommerce.app.common.enums.UserStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // 🔴 Step 1: Check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // 🟢 Step 2: Create User
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        // 🟢 Step 3: Fetch ROLE_USER
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        // 🟢 Step 4: Create mapping (UserRole)
        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(role)
                .build();

        userRoleRepository.save(userRole);

        // 🟢 Step 5: Return response
        return AuthResponse.builder()
                .message("User registered successfully")
                .userId(savedUser.getId())
                .build();
    }
}