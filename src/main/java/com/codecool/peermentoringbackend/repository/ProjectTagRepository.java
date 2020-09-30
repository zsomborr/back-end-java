package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<ProjectEntity, Long> {


    boolean existsProjectEntityByProjectTag(String projectTag);

    ProjectEntity findProjectEntityByProjectTag(String projectTag);
}
