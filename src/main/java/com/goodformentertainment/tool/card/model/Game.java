package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.goodformentertainment.tool.card.model.event.AddPlayerEvent;
import com.goodformentertainment.tool.event.EventDispatcher;

public class Game extends EventDispatcher implements Context {
    private final List<Player> players;
    private final Table table;

    public Game() {
        players = new LinkedList<>();
        table = new Table();
    }

    public void addPlayer(final Player player) {
        if (players.contains(player)) {
            throw new IllegalArgumentException("The Player " + player + " already exists");
        } else {
            players.add(player);
            dispatch(new AddPlayerEvent());
        }
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Table getTable() {
        return table;
    }

    @Override
    public Set<Discard> getDiscards() {
        final Set<Discard> discards = new HashSet<>();
        for (final Placeable placeable : table.getPlaceables()) {
            if (placeable instanceof Discard) {
                discards.add((Discard) placeable);
            }
        }
        return discards;
    }

    @Override
    public Set<Deck> getDecks() {
        final Set<Deck> decks = new HashSet<>();
        for (final Placeable placeable : table.getPlaceables()) {
            if (placeable instanceof Deck) {
                decks.add((Deck) placeable);
            }
        }
        return decks;
    }

    @Override
    public Set<Hand> getHands() {
        final Set<Hand> hands = new HashSet<>();
        for (final Player player : players) {
            hands.add(player.getHand());
        }
        return hands;
    }

    @Override
    public Set<Meld> getMelds() {
        final Set<Meld> melds = new HashSet<>();
        for (final Player player : players) {
            melds.add(player.getMeld());
        }
        return melds;
    }
}
