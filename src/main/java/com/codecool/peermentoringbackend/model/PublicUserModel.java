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
public class PublicUserModel {

    private String firstName;

    private String lastName;

    private String username;

    private String country;

    private String city;

    private String email;

    private Module_ module;

    private List<ProjectEntity> projectTags;

    private List<TechnologyEntity> technologyTags;
}
