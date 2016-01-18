package com.goodformentertainment.tool.card.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardList;
import com.goodformentertainment.tool.card.model.Hand;
import com.goodformentertainment.tool.card.model.Placeable;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class HandView implements View<Hand> {
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
        hand.addObserver(this);

        pane = new FlowPane();
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(5);

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

    @Override
    public void update(final Observable o, final Object arg) {
        LOG.info("update: " + arg);
        if (arg instanceof Placeable.Observe) {
            switch ((Placeable.Observe) arg) {
                case FACING:
                    // Do nothing
                    break;
                case LOCATION:
                    throw new UnsupportedOperationException();
            }
        } else if (arg instanceof CardList.Observe) {
            switch ((CardList.Observe) arg) {
                case LENGTH:
                case ORDER:
                    updateCards();
                    break;
                case TOP:
                    // Do nothing
                    break;
            }
        }
    }

    private void updateCards() {
        pane.getChildren().clear();
        views.clear();

        for (final Card card : hand.getCards()) {
            final CardView view = new CardView(imager, stage);
            view.setCard(card);
            pane.getChildren().add(view.getPane());
            views.add(view);
        }

        // TODO
        // LOG.info("Requesting layout update");
        // pane.requestLayout();
    }
}
