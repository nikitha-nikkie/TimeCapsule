package com.timecapsule.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.timecapsule.backend.entity.Capsule;
import com.timecapsule.backend.service.CapsuleService;

@RestController
@RequestMapping("/api/capsules")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://127.0.0.1:5173"
})
public class CapsuleController {

    private final CapsuleService capsuleService;
    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();

    public CapsuleController(CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createCapsule(
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("recipientEmail") String recipientEmail,
            @RequestParam("deliveryDateTime") String deliveryDateTime,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        try {
            Capsule capsule = new Capsule();
            capsule.setTitle(title);
            capsule.setMessage(message);
            capsule.setRecipientEmail(recipientEmail);
            capsule.setDeliveryDateTime(LocalDateTime.parse(deliveryDateTime));

            if (photo != null && !photo.isEmpty()) {
                String contentType = photo.getContentType();

                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest()
                            .body("Please upload an image file.");
                }

                Files.createDirectories(uploadDir);

                String originalName = Paths.get(
                        photo.getOriginalFilename() == null
                                ? "photo"
                                : photo.getOriginalFilename()
                ).getFileName().toString();

                String extension = "";
                int dot = originalName.lastIndexOf('.');
                if (dot >= 0) {
                    extension = originalName.substring(dot);
                }

                String fileName = UUID.randomUUID() + extension;
                Path destination = uploadDir.resolve(fileName).normalize();

                if (!destination.startsWith(uploadDir)) {
                    return ResponseEntity.badRequest().body("Invalid file name.");
                }

                Files.copy(
                        photo.getInputStream(),
                        destination,
                        StandardCopyOption.REPLACE_EXISTING
                );

                capsule.setPhotoPath(destination.toString());
            }

            Capsule savedCapsule = capsuleService.createCapsule(capsule);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCapsule);

        } catch (IOException | DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body("Could not create capsule. Check the details and try again.");
        }
    }

    @GetMapping
    public List<Capsule> getAllCapsules() {
        return capsuleService.getAllCapsules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Capsule> getCapsuleById(@PathVariable Long id) {
        return capsuleService.getCapsuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Capsule> updateCapsule(
            @PathVariable Long id,
            @RequestBody Capsule capsuleDetails) {
        try {
            return ResponseEntity.ok(
                    capsuleService.updateCapsule(id, capsuleDetails)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCapsule(@PathVariable Long id) {
        try {
            capsuleService.deleteCapsule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}