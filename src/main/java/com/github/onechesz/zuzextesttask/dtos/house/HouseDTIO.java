package com.github.onechesz.zuzextesttask.dtos.house;

import com.github.onechesz.zuzextesttask.models.HouseModel;
import com.github.onechesz.zuzextesttask.models.UserModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;

public class HouseDTIO {
    @NotNull(message = "не может отсутствовать")
    @Size(min = 4, max = 255, message = "должен быть длиной от 4-х до 255-ти символов")
    private String address;

    public HouseDTIO() {

    }

    public HouseDTIO(String address) {
        this.address = address;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @org.jetbrains.annotations.NotNull HouseModel convertToHouseModel(@org.jetbrains.annotations.NotNull HouseDTIO houseDTIO, UserModel ownerModel) {
        return new HouseModel(houseDTIO.address, ownerModel);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
