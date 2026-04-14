package com.app.easy.notes.service;

import com.app.easy.notes.entity.UserEntity;
import com.app.easy.notes.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserEntity loadUserByUsername(String username){
        Optional<UserEntity> user = userRepository.getByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new NoSuchElementException();
        }
    }
}
