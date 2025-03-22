package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Retrieves all notes for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of notes.
     */
    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    /**
     * Retrieves a single note by its ID.
     *
     * @param noteId The ID of the note.
     * @return The note.
     */
    public Note getNoteById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }

    /**
     * Adds a new note for a user.
     *
     * @param note The note to add.
     * @return The number of rows affected.
     */
    public int addNote(Note note) {
        try {
            return noteMapper.insertNote(note);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add note: " + e.getMessage());
        }
    }

    /**
     * Updates an existing note.
     *
     * @param note The note to update.
     * @return The number of rows affected.
     */
    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param noteId The ID of the note to delete.
     * @return The number of rows affected.
     */
    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }
}