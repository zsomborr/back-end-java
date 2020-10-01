package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsModel {

    private List<ProjectEntity> projectTags;

    private List<TechnologyEntity> technologyTags;
}
