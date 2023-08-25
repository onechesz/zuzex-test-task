package com.github.onechesz.zuzextesttask.models;

import com.github.onechesz.zuzextesttask.dtos.user.UserDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "\"user\"")
public class UserModel {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private char[] password;

    @Column(name = "role")
    private String role;

    @Column(name = "age", nullable = false)
    private Integer age;

    public UserModel() {

    }

    public UserModel(String name, char[] password, String role, Integer age) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.age = age;
    }

    @Contract("_ -> new")
    public static @NotNull UserDTOO convertToUserDTOO(@NotNull UserModel userModel) {
        return new UserDTOO(userModel.id, userModel.name, userModel.role, userModel.age);
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
