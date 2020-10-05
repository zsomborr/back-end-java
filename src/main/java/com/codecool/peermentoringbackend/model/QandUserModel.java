package com.codecool.peermentoringbackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QandUserModel {

    Long id;

    String title;

    String description;

    String userId_;

    String username;

}
