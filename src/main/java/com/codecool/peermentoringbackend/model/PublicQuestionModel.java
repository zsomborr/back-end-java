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
public class PublicQuestionModel {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime submissionTime;

    private Long userId;

    private String username;

}
