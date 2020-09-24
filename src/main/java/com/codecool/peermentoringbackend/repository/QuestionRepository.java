package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<UserEntity, Long> {
}
