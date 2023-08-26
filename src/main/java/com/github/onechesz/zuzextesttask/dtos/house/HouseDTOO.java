package com.github.onechesz.zuzextesttask.dtos.house;

import com.github.onechesz.zuzextesttask.dtos.tenant.TenantDTOO;

import java.util.List;

public class HouseDTOO {
    private int id;

    private List<TenantDTOO> tenantDTOOList;

    public HouseDTOO() {

    }

    public HouseDTOO(int id, List<TenantDTOO> tenantDTOOList) {
        this.id = id;
        this.tenantDTOOList = tenantDTOOList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TenantDTOO> getTenantDTOOList() {
        return tenantDTOOList;
    }

    public void setTenantDTOOList(List<TenantDTOO> tenantDTOOList) {
        this.tenantDTOOList = tenantDTOOList;
    }
}
