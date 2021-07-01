package com.codecool.peermentoringbackend.service.validation;

import com.codecool.peermentoringbackend.model.UserModel;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorService {


    public void validateRegistration(UserModel userModel, int minLengthName, int maxLengthName, int minLengthUsername, int maxLengthUsername) throws CredentialException {

        validateName(userModel.getFirstName(), minLengthName, maxLengthName);
        validateName(userModel.getLastName(), minLengthName, maxLengthName);
        validatePassword(userModel.getPassword());
        validateUsername(userModel.getUsername(), minLengthUsername, maxLengthUsername);

    }

    //checks first or last name for length (min and max) and containing characters
    public void validateName(String name, int minLength, int maxLength) throws CredentialException {
        if (!(name.length() >= minLength)) {
            throw new CredentialException(String.format("Name has to be longer than %d characters.", minLength));
        }
        if (!(name.length() <= maxLength)){
            throw new CredentialException(String.format("Name cannot be longer than %d characters.", maxLength));
        }
        if (!isAlpha(name)){
            throw new CredentialException("Name can only contain alphabetic characters.");
        }
    }

    //checks password for min and max length
    public void validatePassword(String password) throws CredentialException {
        if (!isValidPasswordSecure(password)) {
            throw new CredentialException("Password has to contain upper and lowercase characters, at least one digit and at least one special character (@#$%^&+=)!");
        }
    }

    //checks username for min and max length
    public void validateUsername(String username, int minLength, int maxLength) throws CredentialException {
        if (!(username.length() >= minLength)) {
            throw new CredentialException(String.format("Username has to be longer than %d characters.", minLength));
        }
        if (!(username.length() <= maxLength)){
            throw new CredentialException(String.format("Username cannot be longer than %d characters.", maxLength));
        }
        if (!isAlphaNumWithUnderscore(username)){
            throw new CredentialException("Username can only contain alphabetic characters and underscores. Username cannot start or end with underscore.");
        }

    }

    private boolean isAlpha(String string) {
        return string.matches("^\\p{L}+");
    }

    private boolean isAlphaNumWithUnderscore(String string) {
        return string.matches("^[a-zA-Z0-9]([_](?![_])|[a-zA-Z0-9]){2,20}[a-zA-Z0-9]$+");
    }

    private boolean isValidPasswordSecure(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
    }
}
