package com.goodformentertainment.tool.card.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Context;
import com.goodformentertainment.tool.card.model.Meld;
import com.goodformentertainment.tool.card.model.Placeable;
import com.goodformentertainment.tool.card.model.Table;
import com.goodformentertainment.tool.card.model.event.ChangeLengthEvent;
import com.goodformentertainment.tool.event.HandleEvent;

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
    private final List<View<?>> handViews;
    private final Map<Position, MeldView> meldViews;

    public TableView(final Context context, final CardImager imager, final Stage stage,
            final Table table) {
        super(context);
        this.imager = imager;
        this.stage = stage;
        this.table = table;
        handViews = new LinkedList<>();
        meldViews = new HashMap<>();
        table.register(this);

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

        setPane(pane);
        updatePlaceables();
    }

    @Override
    public Table getModel() {
        return table;
    }

    @HandleEvent(type = ChangeLengthEvent.class)
    public void on(final ChangeLengthEvent event) {
        updatePlaceables();
    }

    public void setMeld(final Meld meld, final Position position) {
        final MeldView meldView = new MeldView(context, imager, stage, meld);
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

        if (meldViews.containsKey(position)) {
            meldViews.get(position).removeParent();
            meldViews.put(position, meldView);
        }
        meldView.setParent(this);
    }

    private void updatePlaceables() {
        centerPane.getChildren().clear();
        for (final View<?> view : handViews) {
            view.removeParent();
        }
        handViews.clear();

        for (final Placeable placeable : table.getPlaceables()) {
            if (placeable instanceof Card) {
                final Card card = (Card) placeable;
                final CardView view = new CardView(context, imager, stage);
                view.setParent(this);
                view.setCard(card);
                centerPane.getChildren().add(view.getPane());
                handViews.add(view);
            } else if (placeable instanceof CardStack) {
                final CardStack stack = (CardStack) placeable;
                final StackView view = new StackView(context, imager, stage, stack);
                view.setParent(this);
                centerPane.getChildren().add(view.getPane());
                handViews.add(view);
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
