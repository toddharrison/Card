package com.goodformentertainment.tool.card.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Meld;
import com.goodformentertainment.tool.card.model.Placeable;
import com.goodformentertainment.tool.card.model.Table;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class TableView extends View<Table> {
    private static final Logger LOG = Logger.getLogger(TableView.class);

    private static final String STYLE_TABLE = "table";

    private final CardImager imager;
    private final Stage stage;
    private final Table table;
    private final BorderPane pane;
    private final FlowPane centerPane;
    private final List<View<?>> views;

    public TableView(final CardImager imager, final Stage stage, final Table table) {
        this.imager = imager;
        this.stage = stage;
        this.table = table;
        views = new LinkedList<>();
        table.addObserver(this);

        centerPane = new FlowPane();
        centerPane.setOrientation(Orientation.HORIZONTAL);
        centerPane.setHgap(5);
        centerPane.setVgap(5);
        BorderPane.setMargin(centerPane, new Insets(15));

        centerPane.setMinHeight(CardView.MAX_CARD_SIZE);
        centerPane.setMinWidth(CardView.MAX_CARD_SIZE);

        pane = new BorderPane(centerPane);
        pane.getStyleClass().add(STYLE_TABLE);
        BorderPane.setMargin(pane, new Insets(5));

        updatePlaceables();
    }

    @Override
    public Table getModel() {
        return table;
    }

    @Override
    public BorderPane getPane() {
        return pane;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        LOG.debug("update: " + arg);
        if (arg instanceof Table.Observe) {
            switch ((Table.Observe) arg) {
                case LENGTH:
                    updatePlaceables();
                    break;
            }
        }
    }

    public void setMeld(final Meld meld, final Position position) {
        final MeldView meldView = new MeldView(imager, stage, meld);
        switch (position) {
            case EAST:
                pane.setRight(meldView.getPane());
                break;
            case NORTH:
                pane.setTop(meldView.getPane());
                break;
            case SOUTH:
                pane.setBottom(meldView.getPane());
                break;
            case WEST:
                pane.setLeft(meldView.getPane());
                break;
        }
    }

    private void updatePlaceables() {
        centerPane.getChildren().clear();
        views.clear();

        for (final Placeable placeable : table.getPlaceables()) {
            if (placeable instanceof Card) {
                final Card card = (Card) placeable;
                final CardView view = new CardView(imager, stage);
                view.setCard(card);
                centerPane.getChildren().add(view.getPane());
                views.add(view);
            } else if (placeable instanceof CardStack) {
                final CardStack stack = (CardStack) placeable;
                final StackView view = new StackView(imager, stage, stack);
                centerPane.getChildren().add(view.getPane());
                views.add(view);
            }
        }
    }

    public enum Position {
        NORTH, SOUTH, EAST, WEST;

        public static Iterator<Position> iterator() {
            return Arrays.asList(Position.values()).iterator();
        }
    }
}
