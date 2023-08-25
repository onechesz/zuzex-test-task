package com.github.onechesz.zuzextesttask.dtos.user;

public class UserDTOO {
    private int id;

    private String name;

    private String role;

    private int age;

    public UserDTOO() {

    }

    public UserDTOO(int id, String name, String role, int age) {
        this.id = id;
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
