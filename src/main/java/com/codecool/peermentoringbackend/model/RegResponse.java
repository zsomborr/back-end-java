package com.codecool.peermentoringbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegResponse {
    private boolean success;
    private String message;
}
