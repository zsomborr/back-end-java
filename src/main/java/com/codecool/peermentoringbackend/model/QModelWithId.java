package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QModelWithId {
    private Long id;

    private String title;

    private String description;

    private Long vote;

    private boolean anonym;
}
