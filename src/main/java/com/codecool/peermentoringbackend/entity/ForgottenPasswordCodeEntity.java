package com.codecool.peermentoringbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgottenPasswordCodeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private UserEntity user;

    private Integer code;
}
