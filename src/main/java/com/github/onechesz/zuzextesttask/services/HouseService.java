package com.github.onechesz.zuzextesttask.services;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTIO;
import com.github.onechesz.zuzextesttask.dtos.house.HouseDTOO;
import com.github.onechesz.zuzextesttask.dtos.tenant.TenantDTOO;
import com.github.onechesz.zuzextesttask.models.HouseModel;
import com.github.onechesz.zuzextesttask.models.TenantModel;
import com.github.onechesz.zuzextesttask.models.UserModel;
import com.github.onechesz.zuzextesttask.repositories.HouseRepository;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import com.github.onechesz.zuzextesttask.utils.exceptions.HouseNotProceedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Transactional(readOnly = true)
    public List<HouseDTOO> viewAllSelf(@NotNull UserDetails userDetails) {
        return houseRepository.findAllByOwnerModel(userDetails.getUserModel()).stream().map(houseModel -> {
            List<TenantDTOO> tenantDTOOList = new ArrayList<>();

            for (TenantModel tenantModel : houseModel.getTenantModelList()) {
                UserModel userModel = tenantModel.getUserModel();

                tenantDTOOList.add(new TenantDTOO(userModel.getId(), userModel.getName(), userModel.getAge()));
            }

            return new HouseDTOO(houseModel.getId(), houseModel.getAddress(), tenantDTOOList);
        }).toList();
    }

    public void create(HouseDTIO houseDTIO, @NotNull UserDetails userDetails) {
        houseRepository.save(HouseDTIO.convertToHouseModel(houseDTIO, userDetails.getUserModel()));
    }

    public void update(int id, HouseDTIO houseDTIO, UserDetails userDetails) {
        houseRepository.findById(id).ifPresentOrElse(houseModel -> {
            if (houseModel.getOwnerModel().getId() == userDetails.getUserModel().getId())
                houseModel.setAddress(houseDTIO.getAddress());
            else
                throw new HouseNotProceedException("вы не являетесь владельцем данного дома");
        }, () -> {
            throw new HouseNotProceedException("дома с таким идентификатором не существует");
        });
    }
}
