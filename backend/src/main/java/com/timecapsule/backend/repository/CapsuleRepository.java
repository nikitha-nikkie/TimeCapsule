package com.timecapsule.backend.repository;

import com.timecapsule.backend.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
}