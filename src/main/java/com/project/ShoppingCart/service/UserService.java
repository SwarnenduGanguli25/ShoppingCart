package com.project.ShoppingCart.service;

import com.project.ShoppingCart.dto.ResponseDto;
import com.project.ShoppingCart.dto.SigninDto;
import com.project.ShoppingCart.dto.SigninResponseDto;
import com.project.ShoppingCart.dto.SignupDto;
import com.project.ShoppingCart.exceptions.AuthenticationFailException;
import com.project.ShoppingCart.exceptions.CustomException;
import com.project.ShoppingCart.model.User;
import com.project.ShoppingCart.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public ResponseDto signup(SignupDto signupDto) throws CustomException {
        if (Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("User with this email already exists");
        }

        String encryptedpassword = signupDto.getPassword();
        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedpassword);

        userRepo.save(user);
        ResponseDto responseDto = new ResponseDto("success", "Account is created");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.
                printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SigninResponseDto signin(SigninDto signinDto) throws AuthenticationFailException {
        User user = userRepo.findByEmail(signinDto.getEmail());
        if(Objects.isNull(user)) {
            throw new AuthenticationFailException("Invalid user");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signinDto.getPassword()))) {
                throw new AuthenticationFailException("Wrong Password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new SigninResponseDto("success", "Logged In");
    }
}
