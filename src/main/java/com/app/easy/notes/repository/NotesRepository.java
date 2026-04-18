package com.app.easy.notes.repository;

import com.app.easy.notes.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {
    public Optional<List<NotesEntity>> findByUserId(int userId);
    public Optional<NotesEntity> findById(int id);
}
