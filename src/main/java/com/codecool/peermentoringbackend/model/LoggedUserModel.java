package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggedUserModel {

    private String firstName;

    private String lastName;

    private String username;

    private String country;

    private String city;

    private String email;

    private Module_ module;

    private List<ProjectEntity> projectTags;

    private List<TechnologyEntity> technologyTags;

    private List<ProjectEntity> allProjectTags;

    private List<TechnologyEntity> allTechnologyTags;

    private Long discordId;

    private String discordUsername;

    private Long discriminator;
}
