package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.goodformentertainment.tool.card.model.event.AddPlayerEvent;
import com.goodformentertainment.tool.event.EventDispatcher;

public class Game extends EventDispatcher {
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
}
