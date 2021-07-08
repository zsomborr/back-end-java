package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ForgottenPasswordCodeEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.ForgottenPasswordCodeRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import com.codecool.peermentoringbackend.service.validation.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.*;

@Service
public class ForgottenPasswordService {

    private UserRepository userRepository;
    private EmailSenderService emailSenderService;
    private ValidatorService validatorService;
    private ForgottenPasswordCodeRepository forgottenPasswordCodeRepository;
    private static Random random = new Random();
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ForgottenPasswordService(UserRepository userRepository, EmailSenderService emailSenderService, ValidatorService validatorService, ForgottenPasswordCodeRepository forgottenPasswordCodeRepository) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.validatorService = validatorService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.forgottenPasswordCodeRepository = forgottenPasswordCodeRepository;
    }

    public boolean forgotPassword(String email){
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if(!userEntityOptional.isPresent()){
            throw new NoSuchElementException("User with email " + email + " not found!");
        }
        UserEntity userEntity = userEntityOptional.get();
        int randomNumber = getRandomNumber();
        ForgottenPasswordCodeEntity code = ForgottenPasswordCodeEntity.builder().user(userEntity).code(randomNumber).build();
        forgottenPasswordCodeRepository.save(code);
        String message = String.format("Password forgotten. Use code to set new password. Code: %d", randomNumber);
        emailSenderService.sendMail(email, message, "Forgotten password");
        return true;
    }

    private int getRandomNumber() {
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min) + min;
    }

    public boolean saveNewPassword(Map<String, String> body) throws CredentialException {
        int requestCode = Integer.parseInt(body.get("code"));
        String email = body.get("email");
        String password1 = body.get("password1");
        String password2 = body.get("password2");
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if(!userEntityOptional.isPresent()){
            throw new NoSuchElementException("User with email " + email + " not found!");
        }
        UserEntity userEntity = userEntityOptional.get();
        Optional<ForgottenPasswordCodeEntity> codeOptional = forgottenPasswordCodeRepository.findByUser(userEntity);
        if(!codeOptional.isPresent()){
            throw new IllegalArgumentException("Code is not valid");
        }
        ForgottenPasswordCodeEntity code = codeOptional.get();
        if(requestCode != code.getCode()){
            throw new IllegalArgumentException("Code is not valid");
        }
        if(!password1.equals(password2)){
            throw new IllegalArgumentException("Passwords do not match.");
        }
        validatorService.validatePassword(password1);
        userEntity.setPassword(passwordEncoder.encode(password1));
        userRepository.save(userEntity);
        forgottenPasswordCodeRepository.delete(code);
        return true;
    }
}
