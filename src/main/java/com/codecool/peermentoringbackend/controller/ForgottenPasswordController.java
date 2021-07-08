package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.service.ForgottenPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController()
@RequestMapping("/auth/forgotten-password")
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.netlify.app", "https://peermentor-analytics.netlify.app"}, allowCredentials = "true")
public class ForgottenPasswordController {

    private ForgottenPasswordService forgottenPasswordService;

    @Autowired
    public ForgottenPasswordController(ForgottenPasswordService forgottenPasswordService) {
        this.forgottenPasswordService = forgottenPasswordService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody Map<String, String> body){
        ApiResponse apiResponse = new ApiResponse();
        if(!body.containsKey("email")){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Invalid arguments!");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        String email = body.get("email");
        try{
            forgottenPasswordService.forgotPassword(email);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Code sent to email.");
        } catch (NoSuchElementException e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/save")
    public ResponseEntity<ApiResponse> saveNewPassword(@RequestBody Map<String, String> body){
        ApiResponse apiResponse = new ApiResponse();
        try{
            forgottenPasswordService.saveNewPassword(body);
        } catch (CredentialException | IllegalArgumentException exception) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Password saved successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
