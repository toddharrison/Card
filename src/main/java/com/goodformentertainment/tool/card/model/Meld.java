package com.goodformentertainment.tool.card.model;

public class Meld extends Table {
    private final Player player;

    public Meld(final Player player) {
        super();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setFaceUp(final boolean faceUp) {
        for (final Placeable placeable : getPlaceables()) {
            placeable.setFaceUp(faceUp);
        }
    }
}
