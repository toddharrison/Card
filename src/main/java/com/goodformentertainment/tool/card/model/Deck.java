package com.goodformentertainment.tool.card.model;

public class Deck extends CardStack {
    /**
     * Take all the Cards from the Discard and add them to the bottom of this Deck.
     *
     * @param discard
     */
    public void refresh(final Discard discard) {
        discard.reverse();
        add(discard.takeAll());
    }

    /**
     * Take all the Cards from the Discard, shuffle, and add them to the bottom of this Deck.
     *
     * @param discard
     */
    public void shuffleAndRefresh(final Discard discard) {
        discard.shuffle();
        add(discard.takeAll());
    }
}
