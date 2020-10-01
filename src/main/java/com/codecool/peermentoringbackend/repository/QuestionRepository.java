package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findQuestionEntityById(Long id);

    QuestionEntity findDistinctById(Long id);

    List<QuestionEntity> findQuestionEntitiesByUser(UserEntity user);

}
