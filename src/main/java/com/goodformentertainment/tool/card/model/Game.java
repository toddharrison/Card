package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {
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
            setChanged();
            notifyObservers(Observe.PLAYERS);
        }
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Table getTable() {
        return table;
    }

    public enum Observe {
        PLAYERS
    }
}
