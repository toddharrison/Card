package com.goodformentertainment.tool.card.model;

import java.util.Optional;

public class Card extends Placeable {
    private final String type;
    private CardList list;

    public Card(final String name, final String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void take() {
        if (isPlaced()) {
            pickup();
        } else {
            final Optional<CardList> list = getList();
            if (list.isPresent()) {
                list.get().take(this);
            }
        }
    }

    public Optional<CardList> getList() {
        return Optional.ofNullable(list);
    }

    @Override
    public String toString() {
        return "[Card '" + getName() + "']";
    }

    protected void setList(final CardList list) {
        this.list = list;
    }
}
