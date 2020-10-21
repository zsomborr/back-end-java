package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.DiscordEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscordRepository extends JpaRepository<DiscordEntity, Long> {

    boolean existsByDiscordId(String didcordId);

    DiscordEntity findDistinctByUser(UserEntity userEntity);

    @Query("SELECT DISTINCT d from DiscordEntity d where d.user.id = :userId")
    DiscordEntity getByUserId(@Param("userId") Long userId);
}
