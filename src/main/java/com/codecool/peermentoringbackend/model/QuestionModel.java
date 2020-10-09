package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel {

    private String title;

    private String description;

    private List<String> technologyTags;
}
