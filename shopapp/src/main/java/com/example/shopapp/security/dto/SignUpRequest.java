package com.example.shopapp.security.dto;



import com.example.shopapp.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Builder.Default
    private Role role = Role.ROLE_ADMIN;
}

