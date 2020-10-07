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
public class ProjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column
    private String projectTag;

    @JsonIgnore
    @ManyToMany(mappedBy = "projectTags")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<UserEntity> userEntities;

    public void addUser(UserEntity userEntity) {
        this.userEntities.add(userEntity);
        userEntity.getProjectTags().add(this);
    }

    public void removeUser(UserEntity userEntity) {
        this.userEntities.remove(userEntity);
        userEntity.getProjectTags().remove(this);
    }
}
