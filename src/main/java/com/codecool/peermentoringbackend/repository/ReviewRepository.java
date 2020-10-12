package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
