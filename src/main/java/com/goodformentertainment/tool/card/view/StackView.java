package com.goodformentertainment.tool.card.view;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardList;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Placeable;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StackView implements Observer {
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

        updateStackSizeLabel();
    }

    public CardStack getCardStack() {
        return stack;
    }

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
                    final Optional<Card> card = stack.viewTop();
                    if (card.isPresent()) {
                        cardView.setCard(card.get());
                    } else {
                        cardView.removeCard();
                    }
                    break;
            }
        }
    }

    private void updateStackSizeLabel() {
        stackSize.setText("(" + stack.size() + ")");
    }
}
