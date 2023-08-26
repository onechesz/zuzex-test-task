package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTIO;
import com.github.onechesz.zuzextesttask.dtos.house.HouseDTOO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.HouseService;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.HouseNotCreatedException;
import com.github.onechesz.zuzextesttask.validators.HouseDTIOValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/houses")
public class HouseController {
    private final HouseService houseService;
    private final HouseDTIOValidator houseDTIOValidator;

    public HouseController(HouseService houseService, HouseDTIOValidator houseDTIOValidator) {
        this.houseService = houseService;
        this.houseDTIOValidator = houseDTIOValidator;
    }

    @GetMapping(path = "")
    @ResponseBody
    public List<HouseDTOO> viewAllSelf(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);

        return houseService.viewAllSelf((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<HttpStatus> performCreation(HttpServletRequest httpServletRequest, @RequestBody @Valid HouseDTIO houseDTIO, BindingResult bindingResult) {
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseDTIOValidator.validate(houseDTIO, bindingResult);
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseService.create(houseDTIO, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void checkForHouseCreationExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new HouseNotCreatedException(errorMessagesBuilder.toString());
        }
    }

    @ExceptionHandler(value = HouseNotCreatedException.class)
    private ResponseEntity<ExceptionResponse> houseNotCreatedExceptionHandler(HouseNotCreatedException houseNotCreatedException) {
        return new ResponseEntity<>(new ExceptionResponse(houseNotCreatedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
