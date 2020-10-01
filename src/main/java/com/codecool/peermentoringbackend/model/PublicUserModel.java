package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserModel {

    private String firstName;

    private String lastName;

    private String username;

    private String country;

    private String city;

    private Module_ module;
}
