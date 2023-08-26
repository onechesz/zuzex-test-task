package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTIO;
import com.github.onechesz.zuzextesttask.dtos.house.HouseDTOO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.HouseService;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.HouseNotDeletedException;
import com.github.onechesz.zuzextesttask.utils.exceptions.HouseNotProceedException;
import com.github.onechesz.zuzextesttask.validators.HouseDTIOValidator;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/houses")
@Tag(name = "House Controller", description = "Дома")
public class HouseController {
    private final HouseService houseService;
    private final HouseDTIOValidator houseDTIOValidator;

    public HouseController(HouseService houseService, HouseDTIOValidator houseDTIOValidator) {
        this.houseService = houseService;
        this.houseDTIOValidator = houseDTIOValidator;
    }

    @ApiResponse(responseCode = "200", description = "Список всех домов")
    @Operation(summary = "Запросить список всех своих домов")
    @GetMapping(path = "")
    @ResponseBody
    public List<HouseDTOO> viewAllSelf(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);

        return houseService.viewAllSelf((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PostMapping(path = "/create")
    @Operation(summary = "Создать новый дом")
    @ApiResponse(responseCode = "201", description = "Статус создания нового дома")
    public ResponseEntity<HttpStatus> performCreation(HttpServletRequest httpServletRequest, @RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные о доме", required = true, content = @Content(schema = @Schema(implementation = HouseDTIO.class))) HouseDTIO houseDTIO, BindingResult bindingResult) {
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseDTIOValidator.validate(houseDTIO, bindingResult);
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseService.create(houseDTIO, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные существующего дома")
    @ApiResponse(responseCode = "201", description = "Статус создания нового дома")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> performUpdating(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id, @RequestBody @Valid HouseDTIO houseDTIO, BindingResult bindingResult) {
        authenticationCheck(httpServletRequest);
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseDTIOValidator.validate(houseDTIO, bindingResult);
        checkForHouseCreationExceptionsAndThrow(bindingResult);
        houseService.update(id, houseDTIO, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    private void checkForHouseCreationExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new HouseNotProceedException(errorMessagesBuilder.toString());
        }
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = HouseNotProceedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> houseNotProceedExceptionHandler(@NotNull HouseNotProceedException houseNotProceedException) {
        return new ResponseEntity<>(new ExceptionResponse(houseNotProceedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(path = "/{id}")
    @ApiResponse(responseCode = "202", description = "Статус удаления дома")
    @Operation(summary = "Удаляет дом по идентификатору")
    public ResponseEntity<HttpStatus> performDeletion(HttpServletRequest httpServletRequest, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);
        houseService.delete(id, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = HouseNotDeletedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> houseNotDeletedExceptionHandler(@NotNull HouseNotDeletedException houseNotDeletedException) {
        return new ResponseEntity<>(new ExceptionResponse(houseNotDeletedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
