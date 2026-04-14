package com.app.easy.notes.service;

import com.app.easy.notes.DTO.request.LoginRequestDto;
import com.app.easy.notes.DTO.request.SignupRequestDto;
import com.app.easy.notes.DTO.response.AuthResponse;
import com.app.easy.notes.entity.UserEntity;
import com.app.easy.notes.repository.UserRepository;
import com.app.easy.notes.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequestDto request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String jwt = authUtil.generateJwtToken(user.getUsername());

        return new AuthResponse(user.getName(), user.getUsername(), jwt);
    }

    public Boolean signUp(SignupRequestDto request){
        Optional<UserEntity> user = userRepository.getByUsername(request.getUsername());
        if(user.isPresent()){
            throw new IllegalArgumentException();
        }else{
            UserEntity userEntity = new UserEntity(
                    request.getName(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword())
            );
            userRepository.save(userEntity);
            return true;
        }
    }
}
