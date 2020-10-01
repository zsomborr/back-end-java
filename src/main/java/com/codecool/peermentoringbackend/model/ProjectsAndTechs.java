package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsAndTechs {

    List<ProjectEntity> projects;

    List<TechnologyEntity> technologies;
}
