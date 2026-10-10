package com.timecapsule.backend.service;

import com.timecapsule.backend.entity.Capsule;
import com.timecapsule.backend.repository.CapsuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;

    public CapsuleService(CapsuleRepository capsuleRepository) {
        this.capsuleRepository = capsuleRepository;
    }

    // CREATE
    public Capsule createCapsule(Capsule capsule) {
        return capsuleRepository.save(capsule);
    }

    // READ ALL
    public List<Capsule> getAllCapsules() {
        return capsuleRepository.findAll();
    }

    // READ ONE
    public Optional<Capsule> getCapsuleById(Long id) {
        return capsuleRepository.findById(id);
    }

    // UPDATE
    public Capsule updateCapsule(Long id, Capsule capsuleDetails) {

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capsule not found"));

        capsule.setTitle(capsuleDetails.getTitle());
        capsule.setMessage(capsuleDetails.getMessage());
        capsule.setRecipientEmail(capsuleDetails.getRecipientEmail());
        capsule.setDeliveryDateTime(capsuleDetails.getDeliveryDateTime());
        capsule.setUnlocked(capsuleDetails.isUnlocked());

        return capsuleRepository.save(capsule);
    }

    // DELETE
    public void deleteCapsule(Long id) {
        capsuleRepository.deleteById(id);
    }
}