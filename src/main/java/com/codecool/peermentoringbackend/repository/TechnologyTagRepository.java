package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyTagRepository extends JpaRepository<TechnologyEntity, Long> {

    boolean existsTechnologyEntityByTechnologyTag(String technologyTag);

    TechnologyEntity findTechnologyEntityByTechnologyTag(String technologyTag);
}
