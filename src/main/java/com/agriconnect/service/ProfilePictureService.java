package com.agriconnect.service;

import com.agriconnect.entity.User;
import com.agriconnect.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfilePictureService {

    private final UserRepository userRepository;

    private static final String UPLOAD_DIRECTORY =
            "uploads/profile-pictures";

    private static final long MAX_FILE_SIZE =
            5 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProfilePictureService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }


    // ========================================
    // UPLOAD PROFILE PICTURE
    // ========================================

    public String uploadProfilePicture(
            MultipartFile file,
            Authentication authentication
    ) {

        User user =
                getAuthenticatedUser(authentication);


        // ========================================
        // VALIDATE FILE
        // ========================================

        if (file == null || file.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select an image."
            );
        }


        // ========================================
        // FILE SIZE
        // ========================================

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Profile picture must be 5 MB or smaller."
            );
        }


        // ========================================
        // CONTENT TYPE
        // ========================================

        String contentType =
                file.getContentType();

        if (contentType == null ||
                !ALLOWED_CONTENT_TYPES.contains(
                        contentType.toLowerCase()
                )) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only JPG, PNG and WebP images are allowed."
            );
        }


        // ========================================
        // CREATE UPLOAD DIRECTORY
        // ========================================

        Path uploadPath =
                Paths.get(UPLOAD_DIRECTORY)
                        .toAbsolutePath()
                        .normalize();

        try {

            Files.createDirectories(uploadPath);

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to create upload directory."
            );
        }


        // ========================================
        // FILE EXTENSION
        // ========================================

        String extension =
                getExtension(contentType);


        // ========================================
        // UNIQUE FILE NAME
        // ========================================

        String fileName =
                UUID.randomUUID()
                        .toString()
                        + extension;


        Path targetPath =
                uploadPath.resolve(fileName)
                        .normalize();


        // ========================================
        // SECURITY CHECK
        // ========================================

        if (!targetPath.startsWith(uploadPath)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid file name."
            );
        }


        // ========================================
        // DELETE OLD PICTURE
        // ========================================

        deleteOldPicture(
                user.getProfilePicture()
        );


        // ========================================
        // SAVE NEW FILE
        // ========================================

        try {

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save profile picture."
            );
        }


        // ========================================
        // SAVE PATH TO USER
        // ========================================

        String picturePath =
                "/profile-pictures/"
                        + fileName;

        user.setProfilePicture(
                picturePath
        );

        userRepository.save(user);


        return picturePath;
    }


    // ========================================
    // DELETE PROFILE PICTURE
    // ========================================

    public void deleteProfilePicture(
            Authentication authentication
    ) {

        User user =
                getAuthenticatedUser(authentication);


        deleteOldPicture(
                user.getProfilePicture()
        );


        user.setProfilePicture(null);

        userRepository.save(user);
    }


    // ========================================
    // GET AUTHENTICATED USER
    // ========================================

    private User getAuthenticatedUser(
            Authentication authentication
    ) {

        if (authentication == null ||
                authentication.getName() == null ||
                authentication.getName()
                        .trim()
                        .isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required."
            );
        }


        return userRepository
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found."
                        )
                );
    }


    // ========================================
    // GET EXTENSION
    // ========================================

    private String getExtension(
            String contentType
    ) {

        return switch (
                contentType.toLowerCase()
                ) {

            case "image/png" ->
                    ".png";

            case "image/webp" ->
                    ".webp";

            case "image/jpeg" ->
                    ".jpg";

            default ->
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Unsupported image format."
                    );
        };
    }


    // ========================================
    // DELETE OLD FILE
    // ========================================

    private void deleteOldPicture(
            String picturePath
    ) {

        if (picturePath == null ||
                picturePath.trim().isEmpty()) {

            return;
        }


        try {

            String fileName =
                    Paths.get(picturePath)
                            .getFileName()
                            .toString();

            Path uploadPath =
                    Paths.get(UPLOAD_DIRECTORY)
                            .toAbsolutePath()
                            .normalize();

            Path oldFile =
                    uploadPath
                            .resolve(fileName)
                            .normalize();


            if (oldFile.startsWith(uploadPath)) {

                Files.deleteIfExists(oldFile);
            }

        } catch (Exception e) {

            // Do not fail a new upload simply because
            // an old profile picture could not be deleted.

            System.err.println(
                    "Could not delete old profile picture: "
                            + e.getMessage()
            );
        }
    }
}