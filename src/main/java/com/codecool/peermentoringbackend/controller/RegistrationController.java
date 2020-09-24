package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.RegResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/reg", method = {RequestMethod.GET, RequestMethod.POST})
public class RegistrationController {

    @Autowired
    protected RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public void doRegistration(HttpServletResponse response, @RequestBody UserModel userModel) throws IOException {

        RegResponse regResponse = registrationService.handleRegistration(userModel);
        if (regResponse.isSuccess()) {
            response.setStatus(400);
        } else {
            response.setStatus(200);
        }
        response.getWriter().println(regResponse.getMessage());
    }

}
