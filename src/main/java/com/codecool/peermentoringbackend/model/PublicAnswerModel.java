package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicAnswerModel {

    private Long id;

    private String content;

    private LocalDateTime submissionTime;

    private Long userId;

    private String username;

    private Long questionId;
}
