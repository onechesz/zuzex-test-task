package com.github.onechesz.zuzextesttask.validators;

import com.github.onechesz.zuzextesttask.dtos.user.UserDTIO;
import com.github.onechesz.zuzextesttask.models.UserModel;
import com.github.onechesz.zuzextesttask.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserDTIOValidator implements Validator {
    private final UserRepository userRepository;

    public UserDTIOValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserDTIO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Optional<UserModel> userModelOptional = userRepository.findByName(((UserDTIO) target).getName());

        if (userModelOptional.isPresent())
            errors.rejectValue("name", "", "пользователь с таким именем уже зарегистрирован");
    }
}
