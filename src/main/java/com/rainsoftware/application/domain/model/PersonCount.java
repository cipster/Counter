package com.rainsoftware.application.domain.model;

import com.rainsoftware.application.model.Direction;
import com.rainsoftware.application.model.Poarta;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PersonCount extends BaseEntity {
    private String username;
    private Timestamp recordDate;
    private Direction direction;
    private long currentCount;
    private Poarta poarta;

    @Basic
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "record_date", nullable = false)
    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }

    @Column(name = "direction", nullable = false)
    @Enumerated(EnumType.STRING)
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "current_count", nullable = false)
    public long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(long currentCount) {
        this.currentCount = currentCount;
    }

    @Column(name = "gate", nullable = false)
    @Enumerated(EnumType.STRING)
    public Poarta getPoarta() {
        return poarta;
    }

    public void setPoarta(Poarta poarta) {
        this.poarta = poarta;
    }
}
