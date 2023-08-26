package com.github.onechesz.zuzextesttask.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHouseId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "house_id")
    private int houseId;

    public UserHouseId() {

    }

    public UserHouseId(int userId, int houseId) {
        this.userId = userId;
        this.houseId = houseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHouseId that = (UserHouseId) o;
        return userId == that.userId && houseId == that.houseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, houseId);
    }
}
