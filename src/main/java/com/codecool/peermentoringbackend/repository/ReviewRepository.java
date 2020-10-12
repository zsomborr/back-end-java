package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ReviewEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {


    List<ReviewEntity> findAllByReviewedUser(UserEntity reviewedUser);
}
