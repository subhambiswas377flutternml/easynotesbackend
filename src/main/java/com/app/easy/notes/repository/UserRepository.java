package com.app.easy.notes.repository;

import com.app.easy.notes.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public Optional<UserEntity> getByUsername(String username);
    public Optional<UserEntity> getById(int id);
}
