package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTagModel {

    private Long questionId;

    private String technologyTag;
}
