package com.timecapsule.backend;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.timecapsule.backend.entity.Capsule;
import com.timecapsule.backend.repository.CapsuleRepository;

@Component
public class CapsuleScheduler {

    private final CapsuleRepository capsuleRepository;
    private final EmailService emailService;

    public CapsuleScheduler(
            CapsuleRepository capsuleRepository,
            EmailService emailService) {
        this.capsuleRepository = capsuleRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkCapsules() {
        List<Capsule> capsules = capsuleRepository.findAll();

        for (Capsule capsule : capsules) {
            if (!capsule.isUnlocked()
                    && capsule.getDeliveryDateTime() != null
                    && !capsule.getDeliveryDateTime()
                            .isAfter(LocalDateTime.now())) {

                try {
                    emailService.sendCapsuleEmail(
                            capsule.getRecipientEmail(),
                            capsule.getTitle(),
                            capsule.getMessage(),
                            capsule.getPhotoPath()
                    );

                    capsule.setUnlocked(true);
                    capsuleRepository.save(capsule);

                } catch (Exception e) {
                    System.err.println(
                            "Capsule email failed for ID: "
                                    + capsule.getCapsuleId()
                    );
                    e.printStackTrace();
                }
            }
        }
    }
}