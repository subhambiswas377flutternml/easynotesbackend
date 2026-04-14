package com.app.easy.notes.controller.auth;

import com.app.easy.notes.DTO.request.LoginRequestDto;
import com.app.easy.notes.DTO.request.SignupRequestDto;
import com.app.easy.notes.DTO.response.AuthResponse;
import com.app.easy.notes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Boolean signup(@RequestBody SignupRequestDto request){
        return authService.signUp(request);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthResponse login(@RequestBody LoginRequestDto request){
        return authService.login(request);
    }
}
