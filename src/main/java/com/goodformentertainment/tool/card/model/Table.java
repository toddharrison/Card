package com.goodformentertainment.tool.card.model;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Table {
    private final Set<Placeable> placeables;

    public Table() {
        placeables = new HashSet<>();
    }

    /**
     * Gets an unmodifiable Set of all of the Placeables on this Table.
     * 
     * @return
     */
    public Set<Placeable> getPlaceables() {
        return Collections.unmodifiableSet(placeables);
    }

    /**
     * Places the specified Placeable on this Table at the specified Point.
     * 
     * @param placeable
     * @param point
     */
    public void place(final Placeable placeable, final Point point) {
        placeables.add(placeable);
        placeable.place(new Location(this, point));
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
            return placeable;
        } else {
            throw new IllegalArgumentException("That Placeable is not on this Table");
        }
    }
}
