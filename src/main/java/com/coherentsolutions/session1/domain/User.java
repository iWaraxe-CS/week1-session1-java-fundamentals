package com.coherentsolutions.session1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String name;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Set<Role> roles;
    private boolean active;
    
    public enum Role {
        ADMIN,
        USER,
        MODERATOR
    }
    
    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
    
    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }
}