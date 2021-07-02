package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.netlify.app"}, allowCredentials = "true")
@RequestMapping("/reg")
public class RegistrationController {

    @Autowired
    protected RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public ResponseEntity<ApiResponse> doRegistration(@RequestBody UserModel userModel) throws IOException {

        ApiResponse apiResponse = registrationService.handleRegistration(userModel);
        if(apiResponse.isSuccess()){
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
