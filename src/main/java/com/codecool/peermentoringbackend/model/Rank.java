package com.codecool.peermentoringbackend.model;

import java.util.Optional;

public enum Rank {

    NOVICE(0,  "Novice"),
    APPRENTICE(2,  "Apprentice"),
    PATHFINDER(5,  "Pathfinder"),
    ADVENTURER(10, "Adventurer"),
    FIGHTER(15, "Fighter"),
    SLAYER(20,  "Slayer"),
    MERCENARY(30, "Mercenary"),
    GLADIATOR(45, "Gladiator"),
    VETERAN(65, "Veteran"),
    KEEPER(90,  "Keeper"),
    KNIGHT(120, "Knight"),
    LORD(155, "Lord"),
    HERO(195, "Hero"),
    CHAMPION(250, "Champion"),
    LEGEND(300, "Legend");

    int lowerBoundary;
    String representation;

    Rank(int lowerBoundary, String representation){
        this.lowerBoundary = lowerBoundary;
        this.representation = representation;
    }

    public int getLowerBoundary() {
        return lowerBoundary;
    }

    public String getRepresentation() {
        return representation;
    }

    public static Optional<Rank> getRankByScore(Long score){
        for(int i = Rank.values().length - 1; i >= 0; i--){
            if(score >= Rank.values()[i].getLowerBoundary()){
                return Optional.of(Rank.values()[i]);
            }
        }
        return Optional.empty();
    }

}
