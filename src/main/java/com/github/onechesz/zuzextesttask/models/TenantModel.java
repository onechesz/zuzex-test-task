package com.github.onechesz.zuzextesttask.models;

import com.github.onechesz.zuzextesttask.embeddable.UserHouseId;
import jakarta.persistence.*;

@Entity
@Table(name = "tenant")
public class TenantModel {
    @EmbeddedId
    private UserHouseId userHouseId;

    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @ManyToOne
    @MapsId(value = "houseId")
    @JoinColumn(name = "house_id")
    private HouseModel houseModel;

    public TenantModel() {

    }

    public TenantModel(UserHouseId userHouseId, UserModel userModel, HouseModel houseModel) {
        this.userHouseId = userHouseId;
        this.userModel = userModel;
        this.houseModel = houseModel;
    }

    public UserHouseId getUserHouseId() {
        return userHouseId;
    }

    public void setUserHouseId(UserHouseId userHouseId) {
        this.userHouseId = userHouseId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public HouseModel getHouseModel() {
        return houseModel;
    }

    public void setHouseModel(HouseModel houseModel) {
        this.houseModel = houseModel;
    }
}
