package com.codecool.peermentoringbackend.repository;

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


//    @Query("SELECT DISTINCT u FROM UserEntity u JOIN u.technologyTags t JOIN  u.projectTags p")
//    List<UserEntity> getIfHasTechAndProjectTags();

    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.technologyTags t where t.technologyTag = :techTag")
    List<UserEntity> getIfHasSpecificTechTag( @Param("techTag") TechnologyEntity techTag);

    @Query("SELECT DISTINCT u FROM UserEntity u WHERE u.technologyTags is not empty OR u.projectTags is not empty")
    List<UserEntity> getIfHasProjectOrTechTags();

    @Query("SELECT distinct u FROM UserEntity u  WHERE u.technologyTags in :techTag")
    List<UserEntity> getMentorByTags( @Param("techTag") TechnologyEntity techTag);



}