package com.codecool.peermentoringbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TechnologyEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column
    private String technologyTag;

    @JsonIgnore
    @ManyToMany(mappedBy = "technologyTags")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<UserEntity> userEntities;

    @JsonIgnore
    @ManyToMany(mappedBy = "technologyTags")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<QuestionEntity> questionEntities;

    public void addUser(UserEntity userEntity) {
        this.userEntities.add(userEntity);
        userEntity.getTechnologyTags().add(this);
    }

    public void removeUser(UserEntity userEntity) {
        this.userEntities.remove(userEntity);
        userEntity.getTechnologyTags().remove(this);
    }

    public void addQuestion(QuestionEntity questionEntity) {
        this.questionEntities.add(questionEntity);
        questionEntity.getTechnologyTags().add(this);
    }

    public void removeQuestion(QuestionEntity questionEntity) {
        this.questionEntities.remove(questionEntity);
        questionEntity.getTechnologyTags().remove(this);
    }
}
