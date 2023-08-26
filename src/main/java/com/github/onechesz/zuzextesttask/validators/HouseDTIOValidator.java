package com.github.onechesz.zuzextesttask.validators;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTIO;
import com.github.onechesz.zuzextesttask.repositories.HouseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HouseDTIOValidator implements Validator {
    private final HouseRepository houseRepository;

    public HouseDTIOValidator(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return HouseDTIO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        if (houseRepository.findByAddress(((HouseDTIO) target).getAddress()).isPresent())
            errors.rejectValue("address", "", "дом с таким адресом уже существует");
    }
}
