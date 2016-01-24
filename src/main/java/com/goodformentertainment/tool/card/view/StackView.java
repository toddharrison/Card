package com.goodformentertainment.tool.card.view;

import java.util.Observable;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardList;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.Discard;
import com.goodformentertainment.tool.card.model.Placeable;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StackView extends View<CardStack> {
    private static final Logger LOG = Logger.getLogger(StackView.class);

    private static final String STYLE_STACK_SIZE = "stack-size";

    private final CardStack stack;
    private final StackPane pane;
    private final CardView cardView;
    private final Label stackSize;

    public StackView(final CardImager imager, final Stage stage, final CardStack stack) {
        this.stack = stack;
        stack.addObserver(this);
        cardView = new CardView(imager, stage);
        stackSize = new Label();
        stackSize.getStyleClass().add(STYLE_STACK_SIZE);

        pane = new StackPane();
        pane.getChildren().add(cardView.getPane());
        pane.getChildren().add(stackSize);

        addContextMenu();

        updateStackSizeLabel();
        updateCardView();
    }

    @Override
    public CardStack getModel() {
        return stack;
    }

    @Override
    public StackPane getPane() {
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
                    updateStackSizeLabel();
                    break;
                case ORDER:
                    // Do nothing
                    break;
                case TOP:
                    updateCardView();
                    break;
            }
        }
    }

    private void addContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        if (stack instanceof Deck) {
            final MenuItem draw = new MenuItem("Draw");
            contextMenu.getItems().addAll(draw);
        } else if (stack instanceof Discard) {
            final MenuItem take = new MenuItem("Take");
            contextMenu.getItems().addAll(take);
        } else {
            throw new UnsupportedOperationException(
                    "Unknown CardStack type: " + stack.getClass().getName());
        }

        pane.setOnMousePressed((event) -> {
            if (event.isSecondaryButtonDown()) {
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
                // event.consume();
                LOG.info("Queried for menu");
            }
        });
    }

    private void updateStackSizeLabel() {
        stackSize.setText(Integer.toString(stack.size()));
    }

    private void updateCardView() {
        final Optional<Card> card = stack.viewTop();
        if (card.isPresent()) {
            cardView.setCard(card.get());
        } else {
            cardView.removeCard();
        }
    }
}
