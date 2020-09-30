package com.codecool.peermentoringbackend.service.validation;

import com.codecool.peermentoringbackend.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorService {


    public boolean validateName(String name, int minLength, int maxLength) {
        return isAlpha(name) && name.length() >= minLength && name.length() <= maxLength;
    }

    public boolean validatePassword(String password) {
        return isValidPasswordSecure(password);
    }

    public boolean validateUsername(String username, int minLength, int maxLength) {
        return username.length() >= minLength && username.length() <= maxLength && isAlphaNumWithUnderscore(username);
    }

    public boolean validateRegistration(UserModel userModel, int minLengthName, int maxLengthName, int minLengthUsername, int maxLengthUsername) {

        boolean validFirstName = validateName(userModel.getFirstName(), minLengthName, maxLengthName);
        boolean validLastName = validateName(userModel.getLastName(), minLengthName, maxLengthName);
        boolean validPassword = validatePassword(userModel.getPassword());
        boolean validUsername = validateUsername(userModel.getUsername(), minLengthUsername, maxLengthUsername);

        return validFirstName && validLastName && validPassword && validUsername;
    }

    private boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }

    private boolean isAlphaNumWithUnderscore(String string) {
        return string.matches("^[a-zA-Z0-9_]*$");
    }

    private boolean isValidPasswordSecure(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
    }
}
