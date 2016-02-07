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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[Meld ");
        for (final Placeable placeable : getPlaceables()) {
            sb.append(placeable);
        }
        sb.append("]");
        return sb.toString();
    }
}
