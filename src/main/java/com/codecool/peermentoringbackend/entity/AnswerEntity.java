package com.codecool.peermentoringbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private Long vote;

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

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    Set<UserEntity> voters = new HashSet<>();

    @Column
    private  boolean accepted;

    public void addUser(UserEntity userEntity) {
        this.voters.add(userEntity);
        userEntity.getVotedAnswers().add(this);
    }

    public void removeUser(UserEntity userEntity) {
        this.voters.remove(userEntity);
        userEntity.getVotedAnswers().remove(this);
    }

}
