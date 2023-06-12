package com.InventoryManagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.InventoryManagement.Payloads.*;
import com.InventoryManagement.Services.EmailService;
import com.InventoryManagement.Services.UserService;
import com.InventoryManagement.entities.User;
import com.InventoryManagement.repository.UserRepo;


import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/users")
@CrossOrigin("/api/users/Register")
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    private final EmailService emailService;
    

    @Autowired
    public UserController(UserService userService, UserRepo userRepo, EmailService emailService) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @PostMapping("/Register")
    public ResponseEntity<Userdatatransfer> createUser(@RequestBody Userdatatransfer userDto) {
        Userdatatransfer createdUserDto = this.userService.CreateUser(userDto);

        // Generating verification and saving that token in database
        String verificationToken = UUID.randomUUID().toString();
        User user = userRepo.findByEmail(createdUserDto.getEmail());
        user.setVerificationToken(verificationToken);
        userRepo.save(user);

        String verificationLink = generateVerificationLink(createdUserDto.getEmail(), verificationToken);
        emailService.sendVerificationEmail(createdUserDto.getEmail(), verificationLink);

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);

    }

    private String generateVerificationLink(String email, String token) {
        String baseUrl = "http://localhost:9090"; // Base URL of your application
        String verifyEndpoint = "/verify"; // Verification endpoint

        return baseUrl + verifyEndpoint + "?email=" + email + "&token=" + token;
    }
    
    
   

   
    

    
  
    
    



    @PutMapping("/{userId}")
    public ResponseEntity<Userdatatransfer> updateUser(@RequestBody Userdatatransfer userDto, @PathVariable("userId") Integer Id) {
        Userdatatransfer updatedUser = this.userService.updateUser(userDto, Id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer Id) {
        this.userService.deleteUser(Id);
        return new ResponseEntity<>(Map.of("message", "Information deleted Successfully."), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Userdatatransfer>> getAll() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Userdatatransfer> getUser(@PathVariable("userId") Integer Id) {
        return ResponseEntity.ok(this.userService.getUserById(Id));
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        User user = userRepo.findByEmail(username);

        if (user != null && user.getPassword().equals(password)) {
            // Authentication successful
            return "redirect:/";
        } else {
            // Authentication failed
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
