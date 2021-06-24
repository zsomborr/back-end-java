package com.codecool.peermentoringbackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerModel {

    private Long userId;

    private Long questionId;

    private String content;

}
