package com.goodformentertainment.tool.card.model;

public class Card extends Placeable {
    private final String name;
    private final String type;

    public Card(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}
