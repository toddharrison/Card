package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.goodformentertainment.tool.card.model.event.ChangeLengthEvent;
import com.goodformentertainment.tool.event.EventDispatcher;

public class Table extends EventDispatcher {
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
        dispatch(new ChangeLengthEvent());
    }

    public void place(final List<? extends Placeable> placeables) {
        for (final Placeable placeable : placeables) {
            this.placeables.add(placeable);
            placeable.place(new Location(this, null));
        }
        dispatch(new ChangeLengthEvent());
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
            dispatch(new ChangeLengthEvent());
            return placeable;
        } else {
            throw new IllegalArgumentException("That Placeable is not on this Table");
        }
    }
}
