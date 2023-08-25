package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.UserDTO;
import com.github.onechesz.zuzextesttask.services.UserService;
import com.github.onechesz.zuzextesttask.utils.JWTUtil;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotRegisteredException;
import com.github.onechesz.zuzextesttask.validators.UserDTOValidator;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final UserDTOValidator userDTOValidator;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public AuthController(UserDTOValidator userDTOValidator, UserService userService, JWTUtil jwtUtil) {
        this.userDTOValidator = userDTOValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/register")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        checkForRegisterExceptionsAndThrow(bindingResult);

        userDTOValidator.validate(userDTO, bindingResult);

        checkForRegisterExceptionsAndThrow(bindingResult);

        userService.register(userDTO);

        return Map.of("JWT", jwtUtil.generateToken(userDTO.getName()));
    }

    private void checkForRegisterExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new UserNotRegisteredException(errorMessagesBuilder.toString());
        }
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = UserNotRegisteredException.class)
    private @NotNull ResponseEntity<ExceptionResponse> userNotRegisteredHandler(@NotNull UserNotRegisteredException userNotRegisteredException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotRegisteredException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}