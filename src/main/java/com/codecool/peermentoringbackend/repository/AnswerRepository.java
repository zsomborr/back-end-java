package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
     List<AnswerEntity> findAnswerEntitiesByQuestionId(Long questionId);

     List<AnswerEntity> findAnswerEntitiesByUser(UserEntity user);
}
