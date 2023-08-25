package com.github.onechesz.zuzextesttask.repositories;

import com.github.onechesz.zuzextesttask.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByName(String name);
}
