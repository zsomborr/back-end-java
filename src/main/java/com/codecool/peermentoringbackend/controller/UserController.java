package com.codecool.peermentoringbackend.controller;


import com.codecool.peermentoringbackend.model.DiscordModel;
import com.codecool.peermentoringbackend.model.LoggedUserModel;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.model.UserDataQAndAModel;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.netlify.app"}, allowCredentials = "true")
public class UserController {

    private final AuthenticationManager authenticationManager;


    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenServices jwtTokenServices;


    @GetMapping(value = "/get-user-data/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PublicUserModel getUserData(@PathVariable Long userId) {
        return userService.getPublicUserDataByUserId(userId);
    }

    @GetMapping(value = "/get-user-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoggedUserModel getLoggedInUserData(HttpServletRequest request) {

        return userService.getLoggedInUserData(request);
    }

    @PostMapping("/save-personal-data")
    public void updatePersonalUserData(HttpServletRequest request, @RequestBody PublicUserModel publicUserModel){
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        userService.savePersonalData(publicUserModel, usernameFromToken);
    }

    @GetMapping(value = "/get-user-private-page", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDataQAndAModel getLoggedInUserPage(HttpServletRequest request) {

        return userService.getLoggedInUserPage(request);
    }

    @PostMapping("/discord")
    public void saveDiscordData(@RequestBody DiscordModel discordModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        boolean exists = userService.saveDiscordData(discordModel, usernameFromToken);
        if (exists) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("user already saved discord data");
        }
    }




}
