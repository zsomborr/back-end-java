package com.codecool.peermentoringbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DiscordEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long discordId;

    @Column
    private String discordUsername;

    @Column
    private Long discriminator;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    private UserEntity user;
}
