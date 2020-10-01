package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectEntity, Long> {


    boolean existsProjectEntityByProjectTag(String projectTag);

    ProjectEntity findProjectEntityByProjectTag(String projectTag);

    List<ProjectEntity> findProjectEntitiesByUserEntities(UserEntity userEntity);

}
