package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel {


    private Long userId;

    private String title;

    private String description;
}
