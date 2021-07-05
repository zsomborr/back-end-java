package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.UserRepository;
import com.codecool.peermentoringbackend.service.validation.ValidatorService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.security.auth.login.CredentialException;
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

    public ApiResponse handleRegistration(UserModel userModel) {

        EmailValidator validator = EmailValidator.getInstance();


        if (!validator.isValid(userModel.getEmail())) return new ApiResponse(false, "E-mail format not valid");
        if (userRepository.existsByEmail(userModel.getEmail()))
            return new ApiResponse(false, "Email is already registered");
        if (userRepository.existsByUsername(userModel.getUsername()))
            return new ApiResponse(false, "Username is already taken.");
        int minNameLength = 2;
        int maxNameLength = 20;
        int minUserNameLength = 2;
        int maxUserNameLength = 20;
        try {
            validatorService.validateRegistration(userModel, minNameLength, maxNameLength, minUserNameLength, maxUserNameLength);
        } catch (CredentialException credentialException){
            return new ApiResponse(false, credentialException.getMessage());
        }

        UserEntity userEntity = UserEntity.builder()
                .email(userModel.getEmail())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .username(userModel.getUsername())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .registrationDate(LocalDateTime.now())
                .roles(Collections.singletonList("ROLE_USER"))
                .score(0L)
                .build();
        userRepository.save(userEntity);
        return new ApiResponse(true, "success");
    }

}
