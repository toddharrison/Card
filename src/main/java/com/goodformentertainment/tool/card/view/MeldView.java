package com.goodformentertainment.tool.card.view;

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
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MeldView extends View<Meld> {
    private static final Logger LOG = Logger.getLogger(MeldView.class);

    private static final String STYLE_MELD = "meld";

    private final CardImager imager;
    private final Stage stage;
    private final Meld meld;
    private final FlowPane pane;
    private final List<View<?>> views;

    public MeldView(final CardImager imager, final Stage stage, final Meld meld) {
        this.imager = imager;
        this.stage = stage;
        this.meld = meld;
        views = new LinkedList<>();
        meld.addObserver(this);

        pane = new FlowPane();
        pane.getStyleClass().add(STYLE_MELD);
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(5);

        // 36 = 2 * Padding + 2 * BorderInset + 2 * BorderWidth
        pane.setMinHeight(CardView.MAX_CARD_SIZE + 36);
        pane.setMinWidth(CardView.MAX_CARD_SIZE + 36);

        updatePlaceables();
    }

    @Override
    public Meld getModel() {
        return meld;
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

        for (final Placeable placeable : meld.getPlaceables()) {
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
