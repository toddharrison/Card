package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Table extends Observable {
    private final List<Placeable> placeables;

    public Table() {
        placeables = new LinkedList<>();
    }

    /**
     * Gets an unmodifiable List of all of the Placeables on this Table.
     *
     * @return
     */
    public List<Placeable> getPlaceables() {
        return Collections.unmodifiableList(placeables);
    }

    /**
     * Places the specified Placeable on this Table.
     *
     * @param placeable
     */
    public void place(final Placeable placeable) {
        placeables.add(placeable);
        placeable.place(new Location(this, null));
        setChanged();
        notifyObservers(Observe.LENGTH);
    }

    public boolean has(final Placeable placeable) {
        return placeables.contains(placeable);
    }

    /**
     * Picks up the specified Placeable from this Table.
     *
     * @param placeable
     * @return
     */
    public Placeable pickup(final Placeable placeable) {
        if (placeables.remove(placeable)) {
            placeable.pickup();
            setChanged();
            notifyObservers(Observe.LENGTH);
            return placeable;
        } else {
            throw new IllegalArgumentException("That Placeable is not on this Table");
        }
    }

    public enum Observe {
        LENGTH
    }
}
