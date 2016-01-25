package com.goodformentertainment.tool.card.view;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.Discard;
import com.goodformentertainment.tool.card.model.event.ChangeFirstCardEvent;
import com.goodformentertainment.tool.card.model.event.ChangeLengthEvent;
import com.goodformentertainment.tool.event.HandleEvent;

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
        stack.register(this);
        cardView = new CardView(imager, stage);
        cardView.setParent(this);
        stackSize = new Label();
        stackSize.getStyleClass().add(STYLE_STACK_SIZE);
        stackSize.setMouseTransparent(true);

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

    @HandleEvent(type = ChangeLengthEvent.class)
    public void on(final ChangeLengthEvent event) {
        updateStackSizeLabel();
    }

    @HandleEvent(type = ChangeFirstCardEvent.class)
    public void on(final ChangeFirstCardEvent event) {
        updateCardView();
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
