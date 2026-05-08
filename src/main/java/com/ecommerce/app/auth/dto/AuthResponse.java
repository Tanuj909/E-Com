package com.ecommerce.app.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String message;
    private String token;
    private Long userId;
}