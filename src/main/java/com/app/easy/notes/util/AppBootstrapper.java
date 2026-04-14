package com.app.easy.notes.util;

import com.app.easy.notes.DTO.request.SignupRequestDto;
import com.app.easy.notes.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class AppBootstrapper implements CommandLineRunner {
    @Autowired
    AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("alexm03");
        signupRequestDto.setName("Alex Macias");
        signupRequestDto.setPassword("Abcd@1234");

        authService.signUp(signupRequestDto);
    }
}
