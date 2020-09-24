package com.codecool.peermentoringbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AnswerEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDateTime submissionTime;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long questionId;
}
