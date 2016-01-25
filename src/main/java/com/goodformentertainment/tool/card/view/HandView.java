package com.goodformentertainment.tool.card.view;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Hand;
import com.goodformentertainment.tool.card.model.event.LengthChangeEvent;
import com.goodformentertainment.tool.card.model.event.OrderChangeEvent;
import com.goodformentertainment.tool.event.HandleEvent;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class HandView extends View<Hand> {
    private static final Logger LOG = Logger.getLogger(HandView.class);

    private final CardImager imager;
    private final Stage stage;
    private final Hand hand;
    private final FlowPane pane;
    private final List<CardView> views;

    public HandView(final CardImager imager, final Stage stage, final Hand hand) {
        this.imager = imager;
        this.stage = stage;
        this.hand = hand;
        views = new LinkedList<>();
        hand.register(this);

        pane = new FlowPane();
        pane.getStyleClass().add("hand");
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(5);

        // 20 = 2 * Padding
        pane.setMinHeight(CardView.MAX_CARD_SIZE + 20);
        pane.setMinWidth(CardView.MAX_CARD_SIZE + 20);

        updateCards();
    }

    @Override
    public Hand getModel() {
        return hand;
    }

    @Override
    public FlowPane getPane() {
        return pane;
    }

    @HandleEvent(type = LengthChangeEvent.class)
    public void on(final LengthChangeEvent event) {
        updateCards();
    }

    @HandleEvent(type = OrderChangeEvent.class)
    public void on(final OrderChangeEvent event) {
        updateCards();
    }

    private void updateCards() {
        pane.getChildren().clear();
        for (final View<?> view : views) {
            view.removeParent();
        }
        views.clear();

        for (final Card card : hand.getCards()) {
            final CardView view = new CardView(imager, stage);
            view.setParent(this);
            view.setCard(card);
            pane.getChildren().add(view.getPane());
            views.add(view);
        }
    }
}
