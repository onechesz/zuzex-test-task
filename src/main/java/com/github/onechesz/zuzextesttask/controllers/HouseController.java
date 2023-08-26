package com.github.onechesz.zuzextesttask.controllers;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTOO;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.services.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.onechesz.zuzextesttask.controllers.AuthController.authenticationCheck;

@RestController
@RequestMapping(path = "/api/houses")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(path = "")
    @ResponseBody
    public List<HouseDTOO> viewAllSelf(HttpServletRequest httpServletRequest) {
        authenticationCheck(httpServletRequest);

        return houseService.viewAllSelf((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
