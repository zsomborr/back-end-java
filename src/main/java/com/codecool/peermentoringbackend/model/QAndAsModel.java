package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QAndAsModel {

    private QuestionEntity question;

    private List<String> answers;
}
