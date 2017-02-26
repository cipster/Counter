package com.rainsoftware.application.domain.model;

import com.rainsoftware.application.model.Role;

import javax.persistence.*;

@Entity
public class UserRole extends BaseEntity{
    private String username;
    private Role role;

    @Basic
    @Column(name = "username", nullable = false, insertable = true, updatable = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "role", nullable = false, insertable = true, updatable = true, length = 150)
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
