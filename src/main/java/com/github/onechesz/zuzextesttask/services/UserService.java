package com.github.onechesz.zuzextesttask.services;

import com.github.onechesz.zuzextesttask.dtos.user.UserDTIO;
import com.github.onechesz.zuzextesttask.dtos.user.UserDTOO;
import com.github.onechesz.zuzextesttask.models.UserModel;
import com.github.onechesz.zuzextesttask.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(@NotNull UserDTIO userDTIO) {
        userDTIO.setPassword(passwordEncoder.encode(userDTIO.getPassword()));
        userRepository.save(UserDTIO.convertToUserModel(userDTIO, "ROLE_USER"));
    }

    @Transactional(readOnly = false)
    public List<UserDTOO> getAll() {
        return userRepository.findAll().stream().map(UserModel::convertToUserDTOO).toList();
    }
}
