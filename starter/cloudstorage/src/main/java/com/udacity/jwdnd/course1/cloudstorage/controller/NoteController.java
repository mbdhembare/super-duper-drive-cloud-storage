package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrUpdateNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUserId(authentication.getName());
        note.setUserid(userId);

        if (note.getNotetitle() == null || note.getNotetitle().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Note title cannot be empty.");
            return "redirect:/home";
        }

        if (note.getNotedescription() == null || note.getNotedescription().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Note description cannot be empty.");
            return "redirect:/home";
        }

        try {
            if (note.getNoteid() == null) {
                noteService.addNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "Note added successfully.");
            } else {
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "Note updated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again.");
        }

        return "redirect:/home";
    }
    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully.");
        return "redirect:/home";
    }

    @GetMapping("/edit/{noteId}")
    public String editNote(@PathVariable Integer noteId, Model model) {
        Note note = noteService.getNoteById(noteId);
        model.addAttribute("note", note);
        return "home"; // Return to the home page with the note form populated
    }
}