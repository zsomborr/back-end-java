package com.codecool.peermentoringbackend.model;

import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    private Long vote;

    private boolean anonym;

    private int numberOfAnswers;

    private boolean voted;

    private boolean myQuestion;

    private Set<TechnologyEntity> technologyTags;

}
