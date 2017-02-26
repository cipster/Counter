package com.rainsoftware.application.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rainsoftware.application.service.dateconverter.DateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User extends BaseEntity {
    private String username;
    private String password;
    private LocalDateTime lastLogin;
    private LocalDateTime lastPasswordChange;
    private String firstPage;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String profileImage;
    private String currency;
    private boolean notifiable;

    public User() {
    }

    public User(User user) {
        this.setId(user.getId());
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.lastLogin = user.getLastLogin();
        this.lastPasswordChange = user.getLastPasswordChange();
        this.firstPage = user.getFirstPage();
        this.enabled = user.isEnabled();
        this.createdAt = user.getCreatedAt();
        this.createdBy = user.getCreatedBy();
        this.modifiedAt = user.getModifiedAt();
        this.modifiedBy = user.getModifiedBy();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.profileImage = user.getProfileImage();
        this.currency = user.getCurrency();
        this.notifiable = user.isNotifiable();
    }

    @Basic
    @Column(name = "username", nullable = false, insertable = true, updatable = true, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @JsonIgnore
    @Column(name = "password", nullable = false, insertable = true, updatable = true)
    public String getPassword() {
        return password;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "last_login", nullable = true, insertable = true, updatable = true)
    @Convert(converter = DateTimeConverter.class)
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Basic
    @Column(name = "last_password_change", nullable = true, insertable = true, updatable = true)
    @Convert(converter = DateTimeConverter.class)
    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    @Basic
    @Column(name = "first_page", nullable = true, insertable = true, updatable = true)
    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    @Basic
    @Column(name = "enabled", nullable = false, insertable = true, updatable = true, columnDefinition = "TINYINT default 1")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "first_name", nullable = true, insertable = true, updatable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, insertable = true, updatable = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email", nullable = true, insertable = true, updatable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "profile_image", nullable = true, insertable = true, updatable = true)
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Basic
    @Column(name = "notifiable", nullable = false, insertable = true, updatable = true, columnDefinition = "TINYINT default 1")
    public boolean isNotifiable() {
        return notifiable;
    }

    public void setNotifiable(boolean notifiable) {
        this.notifiable = notifiable;
    }


    @Basic
    @Column(name = "currency", nullable = true)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
