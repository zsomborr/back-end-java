package com.codecool.peermentoringbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @Column(columnDefinition="text")
    private String content;

    @Column
    private LocalDateTime submissionTime;

    @NonNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private UserEntity user;

    @NonNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private QuestionEntity question;


    @Transient
    private Long userId_;

    @Transient
    private String username;

    @Transient
    private Long questionId_;

    @Transient
    private String questionTitle;

    @Transient
    private boolean myAnswer;

    public void setTransientData() {
        this.userId_ = user.getId();
        this.username = user.getUsername();
        this.questionId_ = question.getId();
        this.questionTitle = question.getTitle();
    }

}
