package com.github.onechesz.zuzextesttask.dtos.house;

import com.github.onechesz.zuzextesttask.dtos.tenant.TenantDTOO;

import java.util.List;

public class HouseDTOO {
    private int id;

    private String address;

    private List<TenantDTOO> tenants;

    public HouseDTOO() {

    }

    public HouseDTOO(int id, String address, List<TenantDTOO> tenants) {
        this.id = id;
        this.address = address;
        this.tenants = tenants;
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

    public List<TenantDTOO> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantDTOO> tenants) {
        this.tenants = tenants;
    }
}
