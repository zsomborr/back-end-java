package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QModelWithId {
    private Long id;

    private String title;

    private String description;
}
