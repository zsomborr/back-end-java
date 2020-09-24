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
public class QuestionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime submissionTime;

    @Column
    private Long userId;
}
