package com.timecapsule.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timecapsule.backend.entity.Capsule;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
}