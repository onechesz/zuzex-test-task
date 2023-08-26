package com.github.onechesz.zuzextesttask.repositories;

import com.github.onechesz.zuzextesttask.embeddable.UserHouseId;
import com.github.onechesz.zuzextesttask.models.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<TenantModel, UserHouseId> {

}
