package com.app.easy.notes.service;

import com.app.easy.notes.entity.UserEntity;
import com.app.easy.notes.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserEntity loadUserByUsername(String username){
        return userRepository.getByUsername(username);
    }
}
