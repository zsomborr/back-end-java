package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
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
    private static Random random = new Random();
    private Map<String, Integer> randomNumbers;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ForgottenPasswordService(UserRepository userRepository, EmailSenderService emailSenderService, ValidatorService validatorService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.validatorService = validatorService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.randomNumbers = new HashMap<>();
    }

    public boolean forgotPassword(String email){
        boolean userEmailExists = userRepository.existsByEmail(email);
        if(!userEmailExists){
            throw new NoSuchElementException("User with email " + email + " not found!");
        }
        int min = 100000;
        int max = 999999;
        int randomNumber = random.nextInt(max - min) + min;
        randomNumbers.put(email, randomNumber);
        String message = String.format("Password forgotten. Use code to set new password. Code: %d", randomNumber);
        emailSenderService.sendMail(email, message, "Forgotten password");
        return true;
    }

    public boolean saveNewPassword(Map<String, String> body) throws CredentialException {
        int code = Integer.parseInt(body.get("code"));
        String email = body.get("email");
        String password1 = body.get("password1");
        String password2 = body.get("password2");
        if(code != randomNumbers.get(email)){
            throw new IllegalArgumentException("Code is not valid");
        }
        if(!password1.equals(password2)){
            throw new IllegalArgumentException("Passwords do not match.");
        }
        validatorService.validatePassword(password1);
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setPassword(passwordEncoder.encode(password1));
            userRepository.save(userEntity);
        }
        randomNumbers.remove(email);
        return true;
    }
}
