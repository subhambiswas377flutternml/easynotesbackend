package com.app.easy.notes.repository;

import com.app.easy.notes.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserRepository, Integer> {
    public UserEntity getByUsername(String username);
}
