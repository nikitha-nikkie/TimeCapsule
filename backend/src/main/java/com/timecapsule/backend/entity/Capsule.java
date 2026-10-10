package com.timecapsule.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Capsule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capsuleId;

    private String title;

    private String message;

    private String photoPath;

    private String recipientEmail;

    private LocalDateTime deliveryDateTime;

    private boolean unlocked;

    public Capsule() {
    }

    public Long getCapsuleId() {
        return capsuleId;
    }

    public void setCapsuleId(Long capsuleId) {
        this.capsuleId = capsuleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
    public String getPhotoPath() {
    return photoPath;
}

public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
}
}