package com.ecommerce.app.auth.service.impl;

import com.ecommerce.app.auth.dto.RegisterRequest;
import com.ecommerce.app.auth.dto.AuthResponse;
import com.ecommerce.app.auth.dto.LoginRequest;
import com.ecommerce.app.auth.entity.Role;
import com.ecommerce.app.auth.entity.User;
import com.ecommerce.app.auth.entity.UserRole;
import com.ecommerce.app.auth.repository.RoleRepository;
import com.ecommerce.app.auth.repository.UserRepository;
import com.ecommerce.app.auth.repository.UserRoleRepository;
import com.ecommerce.app.auth.security.CustomUserDetailsService;
import com.ecommerce.app.auth.security.JwtService;
import com.ecommerce.app.auth.service.AuthService;
import com.ecommerce.app.common.enums.UserStatus;
import lombok.RequiredArgsConstructor;

import java.net.Authenticator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    
//    Security Dependency Injections
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

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
        
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(savedUser.getEmail());

        String token =
                jwtService.generateToken(userDetails);

        // 🟢 Step 5: Return response
        return AuthResponse.builder()
                .message("User registered successfully")
                .userId(savedUser.getId())
                .token(token)
                .build();
    }

	@Override
	public AuthResponse login(LoginRequest request) {
		
		Authentication authentication =
				authenticationManager.authenticate(	
						new UsernamePasswordAuthenticationToken
                        (request.getEmail(), 
                         request.getPassword())
                        );			
	    UserDetails userDetails =
	            (UserDetails) authentication.getPrincipal();  //Type Casting to UserDetails
	    
	    String token =
	            jwtService.generateToken(userDetails);
	    
		return AuthResponse.builder()
				.message("Login Success")
				.token(token)
				.build();
	}
    
//    @Override
//    public AuthResponse login(LoginRequest request) {
//    	
//    	//Check If Email Exist or not
//    	User user = userRepository.findByEmail(request.getEmail())
//    			.orElseThrow(() -> new RuntimeException("User With Email Not Found."));
//    	
//    	//Password Match
//    	if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//    		throw new RuntimeException("Incorrect Password!");
//    	}
//    	
//		//JWT Service Yha call Hogi!
//    	
//    	return AuthResponse.builder()
//    			.message("Login Success")
//    			.userId(user.getId())
//    			.build();
//    }
    
    
}