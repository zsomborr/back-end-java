package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologyTagRepository extends JpaRepository<TechnologyEntity, Long> {

    boolean existsTechnologyEntityByTechnologyTag(String technologyTag);

    TechnologyEntity findTechnologyEntityByTechnologyTag(String technologyTag);

    List<TechnologyEntity> findTechnologyEntitiesByUserEntities(UserEntity userEntity);

    List<TechnologyEntity> findAllByOrderByTechnologyTag();
}
