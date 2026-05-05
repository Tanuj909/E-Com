package com.ecommerce.app.config;

import com.ecommerce.app.auth.entity.Role;
import com.ecommerce.app.auth.entity.User;
import com.ecommerce.app.auth.entity.UserRole;
import com.ecommerce.app.auth.repository.RoleRepository;
import com.ecommerce.app.auth.repository.UserRepository;
import com.ecommerce.app.auth.repository.UserRoleRepository;
import com.ecommerce.app.common.enums.UserStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // 🔹 Step 1: Ensure Roles exist
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name("ROLE_USER").build()
                ));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name("ROLE_ADMIN").build()
                ));

        // 🔹 Step 2: Create Admin if not exists
        if (!userRepository.existsByEmail("admin@gmail.com")) {

            User admin = User.builder()
                    .name("Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .status(UserStatus.ACTIVE)
                    .build();

            User savedAdmin = userRepository.save(admin);

            // 🔹 Step 3: Assign ADMIN role
            UserRole mapping = UserRole.builder()
                    .user(savedAdmin)
                    .role(adminRole)
                    .build();

            userRoleRepository.save(mapping);

            System.out.println("✅ Admin user created!");
        } else {
            System.out.println("ℹ️ Admin already exists");
        }
    }
}