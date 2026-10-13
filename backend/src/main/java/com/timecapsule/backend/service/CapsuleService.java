package com.timecapsule.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.timecapsule.backend.entity.Capsule;
import com.timecapsule.backend.repository.CapsuleRepository;

@Service
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;

    public CapsuleService(CapsuleRepository capsuleRepository) {
        this.capsuleRepository = capsuleRepository;
    }

    public Capsule createCapsule(Capsule capsule) {
        capsule.setUnlocked(false);
        return capsuleRepository.save(capsule);
    }

    public List<Capsule> getAllCapsules() {
        return capsuleRepository.findAll();
    }

    public Optional<Capsule> getCapsuleById(Long id) {
        return capsuleRepository.findById(id);
    }

    public Capsule updateCapsule(Long id, Capsule capsuleDetails) {
        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capsule not found"));

        capsule.setTitle(capsuleDetails.getTitle());
        capsule.setMessage(capsuleDetails.getMessage());
        capsule.setRecipientEmail(capsuleDetails.getRecipientEmail());
        capsule.setDeliveryDateTime(capsuleDetails.getDeliveryDateTime());
        capsule.setPhotoPath(capsuleDetails.getPhotoPath());
        capsule.setUnlocked(capsuleDetails.isUnlocked());

        return capsuleRepository.save(capsule);
    }

    public void deleteCapsule(Long id) {
        if (!capsuleRepository.existsById(id)) {
            throw new RuntimeException("Capsule not found");
        }

        capsuleRepository.deleteById(id);
    }
}