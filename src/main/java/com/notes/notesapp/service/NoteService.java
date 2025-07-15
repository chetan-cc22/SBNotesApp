//business logic

package com.notes.notesapp.service;


import com.notes.notesapp.entity.Note;
import com.notes.notesapp.entity.User;
import com.notes.notesapp.repository.NoteRepository;
import com.notes.notesapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepo;

    @Autowired
    private UserRepository userRepo;

    // Create new note
    public Note createNote(Note note, String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        note.setUser(user);
        return noteRepo.save(note);
    }

    // Get all notes for logged-in user
    public List<Note> getNotes(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return noteRepo.findByUser(user);
    }

    // Update a note (if belongs to user)
    public Note updateNote(Long id, Note newNote, String username) {
        Note note = noteRepo.findById(id).orElseThrow();

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        note.setTitle(newNote.getTitle());
        note.setContent(newNote.getContent());

        return noteRepo.save(note);
    }

    // Delete note
    public void deleteNote(Long id, String username) {
        Note note = noteRepo.findById(id).orElseThrow();

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        noteRepo.delete(note);
    }
}
