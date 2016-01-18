package com.goodformentertainment.tool.card.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Placeable;
import com.goodformentertainment.tool.card.model.Table;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class TableView implements View<Table> {
    private static final Logger LOG = Logger.getLogger(HandView.class);

    private static final String STYLE_TABLE = "table";

    private final CardImager imager;
    private final Stage stage;
    private final Table table;
    private final FlowPane pane;
    private final List<View<?>> views;

    public TableView(final CardImager imager, final Stage stage, final Table table) {
        this.imager = imager;
        this.stage = stage;
        this.table = table;
        views = new LinkedList<>();
        table.addObserver(this);

        pane = new FlowPane();
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.getStyleClass().add(STYLE_TABLE);

        updatePlaceables();
    }

    @Override
    public Table getModel() {
        return table;
    }

    @Override
    public FlowPane getPane() {
        return pane;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        LOG.info("update: " + arg);
        if (arg instanceof Table.Observe) {
            switch ((Table.Observe) arg) {
                case LENGTH:
                    updatePlaceables();
                    break;
            }
        }
    }

    private void updatePlaceables() {
        pane.getChildren().clear();
        views.clear();

        for (final Placeable placeable : table.getPlaceables()) {
            if (placeable instanceof Card) {
                final Card card = (Card) placeable;
                final CardView view = new CardView(imager, stage);
                view.setCard(card);
                pane.getChildren().add(view.getPane());
                views.add(view);
            } else if (placeable instanceof CardStack) {
                final CardStack stack = (CardStack) placeable;
                final StackView view = new StackView(imager, stage, stack);
                pane.getChildren().add(view.getPane());
                views.add(view);
            }
        }
    }
}
