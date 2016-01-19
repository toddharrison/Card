package com.goodformentertainment.tool.card.model;

public class Player {
    private final String name;
    private final Hand hand;
    private final Meld meld;

    public Player(final String name) {
        this.name = name;
        hand = new Hand(this);
        meld = new Meld(this);
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public Meld getMeld() {
        return meld;
    }

    @Override
    public String toString() {
        return name;
    }
}
