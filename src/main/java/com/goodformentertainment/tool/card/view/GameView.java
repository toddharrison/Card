package com.goodformentertainment.tool.card.view;

import java.util.Iterator;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Game;
import com.goodformentertainment.tool.card.model.Player;
import com.goodformentertainment.tool.card.view.TableView.Position;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameView extends View<Game> {
    private static final Logger LOG = Logger.getLogger(GameView.class);

    private static final String STYLE_GAME = "game";

    private final CardImager imager;
    private final Stage stage;
    private final Game game;
    private final BorderPane pane;
    private final TableView tableView;

    public GameView(final CardImager imager, final Stage stage, final Game game) {
        this.imager = imager;
        this.stage = stage;
        this.game = game;

        tableView = new TableView(imager, stage, game.getTable());
        tableView.setParent(this);

        pane = new BorderPane();
        pane.getStyleClass().add(STYLE_GAME);
        pane.setCenter(tableView.getPane());

        updatePlayers();
    }

    @Override
    public Game getModel() {
        return game;
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        LOG.info("update: " + arg);
        if (arg instanceof Game.Observe) {
            switch ((Game.Observe) arg) {
                case PLAYERS:
                    updatePlayers();
                    break;
            }
        }
    }

    private void updatePlayers() {
        final Iterator<Position> positions = TableView.Position.iterator();
        for (final Player player : game.getPlayers()) {
            final HandView handView = new HandView(imager, stage, player.getHand());
            final Position position = positions.next();
            switch (position) {
                case NORTH:
                    pane.setTop(handView.getPane());
                    break;
                case SOUTH:
                    pane.setBottom(handView.getPane());
                    break;
                case EAST:
                    pane.setRight(handView.getPane());
                    break;
                case WEST:
                    pane.setLeft(handView.getPane());
                    break;
            }
            tableView.setMeld(player.getMeld(), position);
        }
    }
}
