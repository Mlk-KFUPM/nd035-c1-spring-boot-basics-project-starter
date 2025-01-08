package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;
    private UserService userService;

    public HomeController(HomeService homeService, UserService userService) {
        this.homeService = homeService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);

        model.addAttribute("files", homeService.getFilesView(user.getUserId()));
        model.addAttribute("notes", homeService.findNotesByUserId(user.getUserId()));
        model.addAttribute("credentials", homeService.getAllCredentials(user.getUserId()));
        return "home";
    }

    // Files methods:

    // Upload file
    @PostMapping("/upload_file")
    public String uploadFile(MultipartFile fileUpload, RedirectAttributes redirectAttributes) {
        try {
            if (fileUpload == null || fileUpload.isEmpty()) {
                System.out.println("error null or empty");
                redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
                return "redirect:/home/result";
            }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUser(username);
            if (homeService.findFileByName(fileUpload.getOriginalFilename(), user.getUserId()).isPresent()) {
                System.out.println("error same");
                redirectAttributes.addFlashAttribute("error", "A file with this name already exists.");
                return "redirect:/home/result";
            }
            System.out.println("uploeding");
            System.out.println(fileUpload.getBytes().length);
            homeService.uploadFile(fileUpload, user.getUserId());
            System.out.println("uploaded");
            redirectAttributes.addFlashAttribute("success", "File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Logs detailed error information to the console
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
        }

        return "redirect:/home/result";
    }
    // Delete file
    @PostMapping("/delete_file")
    public String deleteFile(@RequestParam Integer fileId, RedirectAttributes redirectAttributes) {
        try {
            homeService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("success", "File deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "redirect:/home/result";
    }
    // View file:
    @GetMapping("/view_file/{fileId}")
    public String viewFile(@PathVariable Integer fileId, Model model, RedirectAttributes redirectAttributes) {
        try {
            File file = homeService.findFileById(fileId);
            model.addAttribute("file", file);
            return "home"; // Ensure the "home" template exists and handles file details
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to view file: " + e.getMessage());
            return "redirect:/home/result";
        }
    }




    // Notes methods:

    // create new note:
    @PostMapping("/create_note")
    public String saveOrUpdateNote(@RequestParam(required = false) Integer noteId,
                                   @RequestParam String noteTitle,
                                   @RequestParam String noteDescription,
                                   RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);

        if (noteId == null || noteId == 0) {
            homeService.createNote(noteTitle, noteDescription, user.getUserId());
        } else { // updating the note
            homeService.updateNote(noteId, noteTitle, noteDescription);
        }

        return "redirect:/home";
    }

    // delete note:
    @PostMapping("/delete_note")
    public String deleteNote(@RequestParam Integer noteId) {
        homeService.deleteNote(noteId);
        return "redirect:/home";
    }


    // Credentials methods;

    // create new credential:
    @PostMapping("/create_credential")
    public String saveOrUpdateCredential(@RequestParam(required = false) Integer credentialId,
                                         @RequestParam String url,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         RedirectAttributes redirectAttributes) {
        String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username2);
        if (credentialId == null || credentialId == 0) {
            homeService.createCredential(url, username, password, user.getUserId());
        } else {
            homeService.updateCredential(credentialId, url, username, password);
        }
        return "redirect:/home";
    }
    // delete a credential:
    @PostMapping("/delete_credential")
    public String deleteCredential(@RequestParam Integer id) {
        homeService.deleteCredential(id);
        return "redirect:/home";
    }
    // get decrypted password:
    @GetMapping("/decrypt_password")
    @ResponseBody
    public String getDecryptedPassword(@RequestParam Integer id) {
        return homeService.decryptPassword(id);
    }




    @GetMapping("/result")
    public String resultView(Model model) {
        return "result";
    }

}
