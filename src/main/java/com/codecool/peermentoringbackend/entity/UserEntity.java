package com.codecool.peermentoringbackend.entity;

import com.codecool.peermentoringbackend.model.Module_;
import com.codecool.peermentoringbackend.model.Rank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity {

    @Id
    @GeneratedValue

    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    @Column
    private String password;

    @Column
    private LocalDateTime registrationDate;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private Module_ module;

    @Singular
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<QuestionEntity> questions = new HashSet<>();

    @Singular
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<AnswerEntity> answers = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<TechnologyEntity> technologyTags = new HashSet<>();

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<ProjectEntity> projectTags = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<QuestionEntity> votedQuestions = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<AnswerEntity> votedAnswers = new HashSet<>();

    @Singular
    @OneToMany(mappedBy = "reviewedUser", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<ReviewEntity> reviews = new HashSet<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DiscordEntity discordEntity;
}
