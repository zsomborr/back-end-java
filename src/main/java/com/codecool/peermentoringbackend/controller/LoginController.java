package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.LoginCredential;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }


    @GetMapping(value = "/authentication")
    public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String tokenFromRequest = jwtTokenServices.getTokenFromRequest(request);

        boolean authenticated = jwtTokenServices.validateToken(tokenFromRequest);

        if (authenticated) {
           response.setStatus(200);

        } else {
            response.setStatus(401);
            response.getWriter().println("user not authenticated");
        }

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginCredential loginCredential) {
        try {
            String username = loginCredential.getUsername();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginCredential.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());


            String token = jwtTokenServices.createToken(username, roles);
            ResponseCookie cookie = ResponseCookie
                    .from("authentication", token)
                    .maxAge(3600)  //18 hrs
                    .path("/").httpOnly(true).secure(false).build();



            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(){
        ResponseCookie cookie = ResponseCookie
                .from("authentication", "")
                .maxAge(0)
                .path("/").httpOnly(true).secure(false).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("");
    }


}
