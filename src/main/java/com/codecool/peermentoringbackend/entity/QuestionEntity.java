package com.codecool.peermentoringbackend.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QuestionEntity {

    @Id
    @GeneratedValue
    private Long id;


    @Column(columnDefinition="text")
    private String title;

    @Column(columnDefinition="text")
    private String description;

    @Column
    private LocalDateTime submissionTime;

    @Column
    private Long vote;

    @Column
    private boolean anonym;

    @NonNull
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private UserEntity user;

    @Singular
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    Set<AnswerEntity> answers = new HashSet<>();

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<TechnologyEntity> technologyTags = new HashSet<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "votedQuestions")
    Set<UserEntity> voters = new HashSet<>();

    public void addUser(UserEntity userEntity) {
        this.voters.add(userEntity);
        userEntity.getVotedQuestions().add(this);
    }

    public void removeUser(UserEntity userEntity) {
        this.voters.remove(userEntity);
        userEntity.getVotedQuestions().remove(this);
    }
}
