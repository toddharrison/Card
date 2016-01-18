package com.goodformentertainment.tool.card.model;

public class Player {
    private final String name;
    private final Hand hand;

    public Player(final String name) {
        this.name = name;
        hand = new Hand();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }
}
