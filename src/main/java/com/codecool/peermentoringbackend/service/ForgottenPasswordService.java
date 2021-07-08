package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class ForgottenPasswordService {

    private UserRepository userRepository;
    private EmailSenderService emailSenderService;
    private static Random random = new Random();

    @Autowired
    public ForgottenPasswordService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public boolean forgotPassword(String email){
        boolean userEmailExists = userRepository.existsByEmail(email);
        if(!userEmailExists){
            throw new NoSuchElementException("User with email " + email + " not found!");
        }
        int min = 100000;
        int max = 999999;
        int randomNumbers = random.nextInt(max - min) + min;
        String message = String.format("Password forgotten. Use code to set new password. Code: %d", randomNumbers);
        emailSenderService.sendMail(email, message, "Forgotten password");
        return true;
    }
}
