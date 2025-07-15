package com.notes.notesapp.repository;


import com.notes.notesapp.entity.Note;
import com.notes.notesapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    // Get all notes for a user
    List<Note> findByUser(User user);
}
