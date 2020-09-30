package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    UserEntity findDistinctById(Long id);

    UserEntity findDistinctByUsername(String username);
}