package com.codecool.peermentoringbackend.service.validation;

import com.codecool.peermentoringbackend.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {


    //checks first or last name for length (min and max) and containing characters
    public boolean validateName(String name, int minLength, int maxLength) {
        return isAlpha(name) && name.length() >= minLength && name.length() <= maxLength;
    }

    //checks password for min and max length
    public boolean validatePassword(String password, int minLength, int maxLength) {
        return password.length() >= minLength && password.length() <= maxLength;
    }

    //checks username for min and max length
    public boolean validateUsername(String username, int minLength, int maxLength) {
        return username.length() >= minLength && username.length() <= maxLength;
    }

    public boolean validateRegistration(UserModel userModel, int minLengthName, int maxLengthName, int minLengthPassword, int maxLengthPassword, int minLengthUsername, int maxLengthUsername) {

        boolean validFirstName = validateName(userModel.getFirstName(), minLengthName, maxLengthName);
        boolean validLastName = validateName(userModel.getLastName(), minLengthName, maxLengthName);
        boolean validPassword = validatePassword(userModel.getPassword(), minLengthPassword, maxLengthPassword);
        boolean validUsername = validateUsername(userModel.getUsername(), minLengthUsername, maxLengthUsername);

        return validFirstName && validLastName && validPassword && validUsername;
    }

    private boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }
}
