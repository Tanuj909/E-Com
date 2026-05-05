package com.ecommerce.app.auth.service;

import com.ecommerce.app.auth.dto.RegisterRequest;
import com.ecommerce.app.auth.dto.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
}