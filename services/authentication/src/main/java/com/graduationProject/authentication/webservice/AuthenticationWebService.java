package com.graduationProject.authentication.webservice;

import com.graduationProject.authentication.dto.LoginDto;
import com.graduationProject.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AuthenticationWebService {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationWebService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("authentication/login")
    public LoginDto login(@RequestBody LoginDto loginDto) {
        return authenticationService.usernameAndPasswordCombinationIsValid(loginDto);
    }
}
