package com.goodformentertainment.tool.card.model;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

public class Hand extends CardList {
    /**
     * Draw one Card, if available, from the specified Deck into this Hand.
     *
     * @param deck
     */
    public void draw(final Deck deck) {
        final Optional<Card> card = deck.take();
        if (card.isPresent()) {
            add(card.get());
        }
    }

    /**
     * Draw the specified number of Cards, up to the number available, from the specified Deck into
     * this Hand.
     *
     * @param deck
     * @param count
     */
    public void draw(final Deck deck, final int count) {
        final List<Card> cards = deck.take(count);
        add(cards);
    }

    /**
     * Discard the specified Card from this Hand into the specified Discard.
     *
     * @param card
     * @param discard
     */
    public void discard(final Card card, final Discard discard) {
        if (has(card)) {
            take(card);
            discard.addToTop(card);
        } else {
            throw new IllegalArgumentException("That Card is not in this Hand");
        }
    }

    /**
     * Discard all the Cards in this Hand into the specified Discard.
     *
     * @param discard
     */
    public void discardAll(final Discard discard) {
        discard.addToTop(takeAll());
    }

    /**
     * Place the specified Card from this Hand onto the specified Table at the specified Point.
     *
     * @param card
     * @param table
     * @param point
     */
    public void place(final Card card, final Table table, final Point point) {
        if (has(card)) {
            take(card);
            table.place(card, point);
        } else {
            throw new IllegalArgumentException("That Card is not in this Hand");
        }
    }
}
