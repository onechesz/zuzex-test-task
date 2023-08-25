package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotAuthenticatedException;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotAuthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> usernameNotFoundExceptionHandler(@NotNull UsernameNotFoundException usernameNotFoundException) {
        return new ResponseEntity<>(new ExceptionResponse(usernameNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UserNotAuthenticatedException.class)
    public ResponseEntity<ExceptionResponse> userNotAuthenticatedExceptionHandler(@NotNull UserNotAuthenticatedException userNotAuthenticatedException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotAuthenticatedException.getMessage(), System.currentTimeMillis()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UserNotAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> userNotAuthorizedExceptionHandler(@NotNull UserNotAuthorizedException userNotAuthorizedException) {
        return new ResponseEntity<>(new ExceptionResponse(userNotAuthorizedException.getMessage(), System.currentTimeMillis()), HttpStatus.FORBIDDEN);
    }
}
