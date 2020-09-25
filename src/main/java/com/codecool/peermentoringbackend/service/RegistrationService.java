package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.RegResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.UserRepository;
import com.codecool.peermentoringbackend.service.validation.ValidatorService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorService validatorService;

    private final PasswordEncoder passwordEncoder;

    RegistrationService() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public RegResponse handleRegistration(UserModel userModel) {

        EmailValidator validator = EmailValidator.getInstance();


        if (!validator.isValid(userModel.getEmail())) return new RegResponse(false, "e-mail format not valid");
        if (userRepository.existsByEmail(userModel.getEmail()))
            return new RegResponse(false, "this email is already registered");
        if (userRepository.existsByUsername(userModel.getUsername()))
            return new RegResponse(false, "this username is already taken");
        if (!validatorService.validateRegistration(userModel, 2, 20, 8, 20, 2, 20))
            return new RegResponse(false, "registration failed due to invalid credentials");

        UserEntity userEntity = UserEntity.builder()
                .email(userModel.getEmail())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .username(userModel.getUsername())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .registrationDate(LocalDateTime.now())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(userEntity);
        return new RegResponse(true, "success");
    }

}
