package com.github.onechesz.zuzextesttask.dtos.user;

import com.github.onechesz.zuzextesttask.models.UserModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class UserDTIO {
    @NotNull(message = "не должно отсутствовать")
    @Size(min = 4, max = 32, message = "должно быть длиной от 4-х до 32-х символов")
    private String name;

    @NotNull(message = "не должен отсутствовать")
    @Size(min = 7, max = 32, message = "должен быть длиной от 7-ми до 32-х символов")
    private String password;

    @NotNull(message = "не должен отсутствовать")
    @PositiveOrZero(message = "должен быть положительным числом или нолем")
    @Max(value = 120, message = "не должен превышать 120")
    private Integer age;

    public UserDTIO() {

    }

    public static @org.jetbrains.annotations.NotNull UserModel convertToUserModel(@org.jetbrains.annotations.NotNull UserDTIO userDTIO, String role) {
        return new UserModel(userDTIO.name, userDTIO.password.toCharArray(), role, userDTIO.age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
