package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.user.UserDTOO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.adminAuthorizationCheck;
import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    @ResponseBody
    public List<UserDTOO> viewAll(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);
        adminAuthorizationCheck((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return userService.getAll();
    }
}
