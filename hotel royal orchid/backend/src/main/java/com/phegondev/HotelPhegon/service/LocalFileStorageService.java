package com.phegondev.HotelPhegon.service;

import com.phegondev.HotelPhegon.exception.OurException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorageService {

    private final String uploadDir = "uploads/"; // Change this if needed

    public String saveImageLocally(MultipartFile photo) {
        try {
            // Ensure the directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename
            String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            // Save the file
            Files.write(filePath, photo.getBytes());

            // Return the file path (or URL if exposing via API)
            return filePath.toString(); // You can modify this to return a URL if needed

        } catch (IOException e) {
            e.printStackTrace();
            throw new OurException("Unable to save image locally");
        }
    }
}
