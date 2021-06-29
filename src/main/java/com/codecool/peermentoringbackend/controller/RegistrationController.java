package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/reg")
public class RegistrationController {

    @Autowired
    protected RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public void doRegistration(HttpServletResponse response, @RequestBody UserModel userModel) throws IOException {

        ApiResponse apiResponse = registrationService.handleRegistration(userModel);
        if (apiResponse.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
        response.getWriter().println(apiResponse.getMessage());
    }

}
