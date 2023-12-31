package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.AuthenticationDTO;
import com.github.onechesz.zuzextesttask.dtos.user.UserDTIO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.UserService;
import com.github.onechesz.zuzextesttask.utils.JWTUtil;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotAuthenticatedException;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotAuthorizedException;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotRegisteredException;
import com.github.onechesz.zuzextesttask.validators.UserDTIOValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
@Tag(name = "Auth Controller", description = "Регистрация и вход")
public class AuthController {
    private final UserDTIOValidator userDTIOValidator;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserDTIOValidator userDTIOValidator, UserService userService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDTIOValidator = userDTIOValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public static void authenticationCheck(@NotNull HttpServletRequest httpServletRequest) {
        UserNotAuthenticatedException userNotAuthenticatedException = (UserNotAuthenticatedException) httpServletRequest.getAttribute("exception");

        if (userNotAuthenticatedException != null)
            throw userNotAuthenticatedException;
    }

    public static void adminAuthorizationCheck(@NotNull UserDetails userDetails) {
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            throw new UserNotAuthorizedException("у вас нет прав администратора");
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Зарегистрировать нового пользователя")
    @ApiResponse(responseCode = "200", description = "JWT")
    public Map<String, String> performRegistration(@RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные о пользователе", required = true, content = @Content(schema = @Schema(implementation = UserDTIO.class))) UserDTIO userDTIO, BindingResult bindingResult) {
        checkForRegistrationExceptionsAndThrow(bindingResult);
        userDTIOValidator.validate(userDTIO, bindingResult);
        checkForRegistrationExceptionsAndThrow(bindingResult);
        userService.register(userDTIO);

        return Map.of("JWT", jwtUtil.generateToken(userDTIO.getName()));
    }

    private void checkForRegistrationExceptionsAndThrow(@NotNull BindingResult bindingResult) {
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
    private @NotNull ResponseEntity<ExceptionResponse> userNotRegisteredExceptionHandler(@NotNull UserNotRegisteredException userNotRegisteredException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotRegisteredException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Войти в качестве пользователя")
    @ApiResponse(responseCode = "200", description = "JWT")
    public Map<String, String> performLogin(@RequestBody @Valid @NotNull @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Входные данные пользователя", required = true, content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))) AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getName(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new UserNotAuthenticatedException("неверные данные для входа");
        }

        return Map.of("JWT", jwtUtil.generateToken(authenticationDTO.getName()));
    }
}
