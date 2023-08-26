package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.TenantService;
import com.github.onechesz.zuzextesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.zuzextesttask.utils.exceptions.TenantNotAddedException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/houses/{houseId}/tenants")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> performAddingToHouse(HttpServletRequest httpServletRequest, @PathVariable(name = "houseId") int houseId, @PathVariable(name = "id") int id) {
        authenticationCheck(httpServletRequest);
        tenantService.addToHouse(id, houseId, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = TenantNotAddedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> tenantNotAddedExceptionHandler(@NotNull TenantNotAddedException tenantNotAddedException) {
        return new ResponseEntity<>(new ExceptionResponse(tenantNotAddedException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }
}
