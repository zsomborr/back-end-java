package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ForgottenPasswordCodeEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgottenPasswordCodeRepository extends JpaRepository<ForgottenPasswordCodeEntity, Long> {


    Optional<ForgottenPasswordCodeEntity> findByUser(UserEntity userEntity);
}
