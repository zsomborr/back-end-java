package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.service.ForgottenPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController()
@RequestMapping("/auth/forgotten-password")
public class ForgottenPasswordController {

    private ForgottenPasswordService forgottenPasswordService;

    @Autowired
    public ForgottenPasswordController(ForgottenPasswordService forgottenPasswordService) {
        this.forgottenPasswordService = forgottenPasswordService;
    }

    @PostMapping(path = "/")
    public ApiResponse forgotPassword(@RequestBody String email){
        ApiResponse apiResponse = new ApiResponse();
        try{
            forgottenPasswordService.forgotPassword(email);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Code sent to email.");
        } catch (NoSuchElementException e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

}
