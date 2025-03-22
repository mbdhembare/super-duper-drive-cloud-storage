package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String errorMessage = null;

        // Validate input fields
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errorMessage = "Username cannot be empty.";
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errorMessage = "Password cannot be empty.";
        } else if (!userService.isUsernameAvailable(user.getUsername())) {
            errorMessage = "Username already exists.";
        }

        if (errorMessage == null) {
            // Create the user
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 1) {
                errorMessage = "There was an error signing you up. Please try again.";
            }
        }

        if (errorMessage == null) {
            // Redirect to login page with success message
            redirectAttributes.addFlashAttribute("successMessage", "You successfully signed up! Please log in.");
            return "redirect:/login";
        } else {
            // Show error message on the signup page
            model.addAttribute("errorMessage", errorMessage);
            return "signup";
        }

    }
}