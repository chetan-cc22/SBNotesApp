package com.notes.notesapp.controller;


import com.notes.notesapp.entity.Note;
import com.notes.notesapp.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // 🟢 Create Note
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note, Authentication auth) {
        String username = auth.getName(); // get current logged-in username
        return ResponseEntity.ok(noteService.createNote(note, username));
    }

    // 🔵 Get All Notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(noteService.getNotes(username));
    }

    // 🟠 Update Note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id,
                                           @RequestBody Note note,
                                           Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(noteService.updateNote(id, note, username));
    }

    // 🔴 Delete Note
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        noteService.deleteNote(id, username);
        return ResponseEntity.ok("Note deleted successfully");
    }
}
