package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q from QuestionEntity q ORDER BY q.submissionTime desc")
    List<QuestionEntity> findAllDesc();

    QuestionEntity findQuestionEntityById(Long id);

    QuestionEntity findDistinctById(Long id);

    List<QuestionEntity> findQuestionEntitiesByUser(UserEntity user);

    Optional<List<QuestionEntity>> findDistinctByTitleContainingOrDescriptionContaining(String wordOne, String wordTwo);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE QuestionEntity q SET q.title = :title, q.description = :description where q.id = :questionId")
    void editQuestion(@Param("title") String title, @Param("description") String description, @Param("questionId") Long questionId);

    QuestionEntity findQuestionEntityByUser(UserEntity userEntity);

}
