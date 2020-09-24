package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
     List<AnswerEntity> findAnswerEntitiesByQuestionId(Long questionId);
}
