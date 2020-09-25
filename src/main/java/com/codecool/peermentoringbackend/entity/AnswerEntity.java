package com.codecool.peermentoringbackend.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @NonNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NonNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private QuestionEntity question;
}
