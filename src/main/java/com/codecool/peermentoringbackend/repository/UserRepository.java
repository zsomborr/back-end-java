package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    UserEntity findDistinctById(Long id);

    UserEntity findDistinctByUsername(String username);

    Optional<List<UserEntity>> findDistinctByTechnologyTagsInOrProjectTagsIn(Set<TechnologyEntity> technologyTags, Set<ProjectEntity> projectTags);

    Optional<List<UserEntity>> findDistinctByTechnologyTagsIn(Set<TechnologyEntity> technologyTags);

    Optional<List<UserEntity>> findDistinctByProjectTagsIn(Set<ProjectEntity> projectTags);

    @Query("SELECT DISTINCT u FROM UserEntity u WHERE u.technologyTags is not empty OR u.projectTags is not empty")
    List<UserEntity> getIfHasProjectOrTechTags();


    @Query("SELECT u from UserEntity u join fetch u.questions q where q.id = :questionId ")
    UserEntity findUserEntityByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT u from UserEntity u join fetch u.answers a where a.id = :answerId ")
    UserEntity findUserEntityByAnswerId(@Param("answerId") Long answerId);

    @Query("SELECT COALESCE(SUM(qe.vote), 0) AS vote FROM UserEntity ue LEFT JOIN QuestionEntity qe on ue.id = qe.user.id WHERE ue.id = :userId GROUP BY ue.id")
    Long getUserQuestionScore(Long userId);

    @Query("SELECT COALESCE(SUM(ae.vote), 0) AS vote FROM UserEntity ue LEFT JOIN AnswerEntity ae on ue.id = ae.user.id WHERE ue.id = :userId GROUP BY ue.id")
    Long getUserAnswerScore(Long userId);

    Optional<UserEntity> findByEmail(String email);
}