package com.github.onechesz.zuzextesttask.services;

import com.github.onechesz.zuzextesttask.dtos.house.HouseDTOO;
import com.github.onechesz.zuzextesttask.dtos.tenant.TenantDTOO;
import com.github.onechesz.zuzextesttask.models.TenantModel;
import com.github.onechesz.zuzextesttask.models.UserModel;
import com.github.onechesz.zuzextesttask.repositories.HouseRepository;
import com.github.onechesz.zuzextesttask.security.UserDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

            return new HouseDTOO(houseModel.getId(), tenantDTOOList);
        }).toList();
    }
}
