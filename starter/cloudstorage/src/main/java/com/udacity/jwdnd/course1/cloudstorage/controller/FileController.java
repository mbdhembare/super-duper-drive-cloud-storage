package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getFiles(Authentication authentication, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        model.addAttribute("files", fileService.getFilesByUserId(userId));
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        Integer userId = userService.getUserId(authentication.getName());
        String errorMessage = null;

        if (file.isEmpty()) {
            errorMessage = "Empty file! Please select a file to upload.";
        }

        if (errorMessage == null && !fileService.isFilenameAvailable(file.getOriginalFilename(), userId)) {
            errorMessage = "File with the same name already exists.";
        }

        if (errorMessage == null) {
            int rowsAdded = fileService.uploadFile(file, userId);
            if (rowsAdded < 1) {
                errorMessage = "There was an error uploading your file. Please try again.";
            }
        }

        if (errorMessage == null) {
            redirectAttributes.addFlashAttribute("successMessage", "File uploaded successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        return "redirect:/home";
    }

    @GetMapping("/view/{fileId}")
    public void viewFile(@PathVariable Integer fileId, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(fileId);
        if (file != null) {
            response.setContentType(file.getContenttype());
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
            response.getOutputStream().write(file.getFiledata());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes) {
        fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("successMessage", "File deleted successfully.");
        return "redirect:/home";
    }
}