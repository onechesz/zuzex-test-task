package com.github.onechesz.zuzextesttask.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "house")
public class HouseModel {
    @Column(name = "id", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private UserModel ownerModel;

    @OneToMany(mappedBy = "houseModel", cascade = CascadeType.ALL)
    private List<TenantModel> tenantModelList;

    public HouseModel() {

    }

    public HouseModel(String address, UserModel ownerModel) {
        this.address = address;
        this.ownerModel = ownerModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserModel getOwnerModel() {
        return ownerModel;
    }

    public void setOwnerModel(UserModel ownerUserModel) {
        this.ownerModel = ownerUserModel;
    }

    public List<TenantModel> getTenantModelList() {
        return tenantModelList;
    }

    public void setTenantModelList(List<TenantModel> tenantModelList) {
        this.tenantModelList = tenantModelList;
    }
}
