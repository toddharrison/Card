package com.goodformentertainment.tool.card.model;

import java.util.Set;

public interface Context {
    Set<Discard> getDiscards();

    Set<Deck> getDecks();

    Set<Hand> getHands();

    Set<Meld> getMelds();
}
