package com.goodformentertainment.tool.card.view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Game;
import com.goodformentertainment.tool.card.model.Player;
import com.goodformentertainment.tool.card.model.event.AddPlayerEvent;
import com.goodformentertainment.tool.card.view.TableView.Position;
import com.goodformentertainment.tool.event.HandleEvent;

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
    private final List<HandView> views;

    public GameView(final CardImager imager, final Stage stage, final Game game) {
        this.imager = imager;
        this.stage = stage;
        this.game = game;
        views = new LinkedList<>();

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

    @HandleEvent(type = AddPlayerEvent.class)
    public void on(final AddPlayerEvent event) {
        updatePlayers();
    }

    private void updatePlayers() {
        for (final View<?> view : views) {
            view.removeParent();
        }
        views.clear();

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
            handView.setParent(this);
            views.add(handView);
            tableView.setMeld(player.getMeld(), position);
        }
    }
}
