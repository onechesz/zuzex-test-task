package com.github.onechesz.zuzextesttask.services;

import com.github.onechesz.zuzextesttask.embeddable.UserHouseId;
import com.github.onechesz.zuzextesttask.models.TenantModel;
import com.github.onechesz.zuzextesttask.repositories.HouseRepository;
import com.github.onechesz.zuzextesttask.repositories.TenantRepository;
import com.github.onechesz.zuzextesttask.repositories.UserRepository;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.utils.exceptions.TenantNotAddedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TenantService {
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    public TenantService(HouseRepository houseRepository, UserRepository userRepository, TenantRepository tenantRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    public void addToHouse(int id, int houseId, UserDetails userDetails) {
        houseRepository.findById(houseId).ifPresentOrElse(houseModel -> userRepository.findById(id).ifPresentOrElse(userModel -> {
            if (userDetails.getUserModel().getId() == houseModel.getOwnerModel().getId())
                tenantRepository.save(new TenantModel(new UserHouseId(userModel.getId(), houseId), userModel, houseModel));
            else
                throw new TenantNotAddedException("вы не являетесь владельцем данного дома");
        }, () -> {
            throw new TenantNotAddedException("пользователь с таким идентификатором не найден");
        }), () -> {
            throw new TenantNotAddedException("дом с таким идентификатором не найден");
        });
    }
}
