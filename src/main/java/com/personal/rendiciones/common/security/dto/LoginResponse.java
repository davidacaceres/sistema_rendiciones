package com.personal.rendiciones.common.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String refreshToken;
    private long expiresIn;
    private long refreshExpiresIn;
    private String username;
    private String name;
    private List<String> roles;
}
