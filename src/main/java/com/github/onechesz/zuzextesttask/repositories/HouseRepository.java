package com.github.onechesz.zuzextesttask.repositories;

import com.github.onechesz.zuzextesttask.models.HouseModel;
import com.github.onechesz.zuzextesttask.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<HouseModel, Integer> {
    List<HouseModel> findAllByOwnerModel(UserModel ownerModel);

    Optional<HouseModel> findByAddress(String address);
}
