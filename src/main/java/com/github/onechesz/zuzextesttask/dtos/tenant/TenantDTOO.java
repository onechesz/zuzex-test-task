package com.github.onechesz.zuzextesttask.dtos.tenant;

public class TenantDTOO {
    private int id;

    private String name;

    private int age;

    public TenantDTOO() {

    }

    public TenantDTOO(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
