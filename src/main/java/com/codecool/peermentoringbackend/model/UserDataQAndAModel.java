package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataQAndAModel {

    private String firstName;

    private String lastName;

    private String username;

    private String country;

    private String city;

    private Module_ module;

    private List<QuestionEntity> userQuestions;

    private List<AnswerEntity> userAnswers;
}
