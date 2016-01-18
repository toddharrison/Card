package com.goodformentertainment.tool.card.model;

import java.util.Observable;
import java.util.Optional;

public abstract class Placeable extends Observable {
    protected boolean faceUp = true;
    protected Optional<Location> location;

    /**
     * Determines if this Placeable should be face up to all Players if placed.
     *
     * @return
     */
    public boolean isFaceUp() {
        return faceUp;
        // TODO
        // return faceUp && location.isPresent();
    }

    /**
     * Sets if this Placeable should be face up to all Players if placed.
     *
     * @param visible
     */
    public void setFaceUp(final boolean faceUp) {
        if (this.faceUp != faceUp) {
            this.faceUp = faceUp;
            setChanged();
            notifyObservers(Observe.FACING);
        }
    }

    /**
     * Get the Location, if appropriate, of this Placeable.
     *
     * @return
     */
    public Optional<Location> getLocation() {
        return location;
    }

    /**
     * Determines if this Placeable has been placed on the Table.
     *
     * @return
     */
    public boolean isPlaced() {
        return location.isPresent();
    }

    /**
     * Place this Placeable at the specified location.
     *
     * @param location
     */
    public void place(final Location location) {
        this.location = Optional.of(location);
        setChanged();
        notifyObservers(Observe.LOCATION);
    }

    /**
     * Pickup this Placeable.
     */
    public void pickup() {
        if (location.isPresent()) {
            location = Optional.empty();
            setChanged();
            notifyObservers(Observe.LOCATION);
        }
    }

    public enum Observe {
        FACING, LOCATION
    }
}
