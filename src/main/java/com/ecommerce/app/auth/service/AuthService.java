package com.ecommerce.app.auth.service;

import com.ecommerce.app.auth.dto.RegisterRequest;
import com.ecommerce.app.auth.dto.AuthResponse;
import com.ecommerce.app.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);
}