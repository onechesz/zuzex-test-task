package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.user.UserDTIO;
import com.github.onechesz.zuzextesttask.dtos.user.UserDTOO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.UserService;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotUpdatedException;
import com.github.onechesz.zuzextesttask.validators.UserDTIOValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.adminAuthorizationCheck;
import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService userService;
    private final UserDTIOValidator userDTIOValidator;

    public UserController(UserService userService, UserDTIOValidator userDTIOValidator) {
        this.userService = userService;
        this.userDTIOValidator = userDTIOValidator;
    }

    @GetMapping(path = "")
    @ResponseBody
    public List<UserDTOO> viewAll(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);
        adminAuthorizationCheck((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return userService.getAll();
    }

    @PatchMapping(path = "")
    public ResponseEntity<HttpStatus> performUpdating(HttpServletRequest httpServletRequest, @RequestBody @Valid @NotNull UserDTIO userDTIO, BindingResult bindingResult) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        authenticationCheck(httpServletRequest);
        checkForUpdatingExceptionsAndThrow(bindingResult);

        if (!userDetails.getUserModel().getName().equals(userDTIO.getName())) {
            userDTIOValidator.validate(userDTIO, bindingResult);
            checkForUpdatingExceptionsAndThrow(bindingResult);
        }

        userService.update(userDTIO, userDetails);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    private void checkForUpdatingExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new UserNotUpdatedException(errorMessagesBuilder.toString());
        }
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = UserNotUpdatedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> userNotUpdatedExceptionHandler(@NotNull UserNotUpdatedException userNotUpdatedException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotUpdatedException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "")
    public ResponseEntity<HttpStatus> performDeleting(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);

        userService.delete((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
