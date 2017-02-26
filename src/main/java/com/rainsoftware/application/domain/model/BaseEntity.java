package com.rainsoftware.application.domain.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rainsoftware.application.common.AuthUtils;
import com.rainsoftware.application.service.dateconverter.DateDeserializer;
import com.rainsoftware.application.service.dateconverter.DateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    protected LocalDateTime createdAt;
    protected String createdBy;
    protected LocalDateTime modifiedAt;
    protected String modifiedBy;
    private long id;
    private boolean deleted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "modified_at", insertable = false, updatable = true)
    @Convert(converter = DateTimeConverter.class)
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Column(name = "modified_by", nullable = true, insertable = true, updatable = true, length = 150)
    public String getModifiedBy() {
        return modifiedBy;
    }

    void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @JsonSerialize(using = DateDeserializer.class)
    @Column(name = "created_at", insertable = true, nullable = false, updatable = false)
    @Convert(converter = DateTimeConverter.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "created_by", nullable = false, insertable = true, updatable = true, length = 150)
    public String getCreatedBy() {
        return createdBy;
    }

    void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "deleted", nullable = true, insertable = true, updatable = true, columnDefinition = "TINYINT default 0")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @PrePersist
    void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(AuthUtils.getLoggedInUsername());
    }

    @PreUpdate
    void onPersist() {
        this.setModifiedAt(LocalDateTime.now());
        this.setModifiedBy(AuthUtils.getLoggedInUsername());
    }
}
