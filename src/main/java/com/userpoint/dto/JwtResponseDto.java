package com.userpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtResponseDto(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }
}
