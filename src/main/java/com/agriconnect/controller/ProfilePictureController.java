package com.agriconnect.controller;

import com.agriconnect.service.ProfilePictureService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/users/profile-picture")
@CrossOrigin(origins = "*")
public class ProfilePictureController {

    private final ProfilePictureService profilePictureService;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProfilePictureController(
            ProfilePictureService profilePictureService
    ) {
        this.profilePictureService =
                profilePictureService;
    }


    // ========================================
    // UPLOAD PROFILE PICTURE
    // ========================================

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {

        String picturePath =
                profilePictureService.uploadProfilePicture(
                        file,
                        authentication
                );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Profile picture uploaded successfully.",

                        "profilePicture",
                        picturePath
                )
        );
    }


    // ========================================
    // DELETE PROFILE PICTURE
    // ========================================

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteProfilePicture(
            Authentication authentication
    ) {

        profilePictureService.deleteProfilePicture(
                authentication
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Profile picture deleted successfully."
                )
        );
    }
}