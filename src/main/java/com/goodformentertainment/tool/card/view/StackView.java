package com.goodformentertainment.tool.card.view;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.CardStack;
import com.goodformentertainment.tool.card.model.Context;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.event.ChangeFirstCardEvent;
import com.goodformentertainment.tool.card.model.event.ChangeLengthEvent;
import com.goodformentertainment.tool.event.HandleEvent;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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

    public StackView(final Context context, final CardImager imager, final Stage stage,
            final CardStack stack) {
        super(context);
        this.stack = stack;
        stack.register(this);
        cardView = new CardView(context, imager, stage);
        cardView.setParent(this);
        stackSize = new Label();
        stackSize.getStyleClass().add(STYLE_STACK_SIZE);
        stackSize.setMouseTransparent(true);

        pane = new StackPane();
        pane.getChildren().add(cardView.getPane());
        pane.getChildren().add(stackSize);

        setPane(pane);
        updateStackSizeLabel();
        updateCardView();
    }

    @Override
    public CardStack getModel() {
        return stack;
    }

    @HandleEvent(type = ChangeLengthEvent.class)
    public void on(final ChangeLengthEvent event) {
        updateStackSizeLabel();
    }

    @HandleEvent(type = ChangeFirstCardEvent.class)
    public void on(final ChangeFirstCardEvent event) {
        updateCardView();
    }

    @Override
    public Optional<Menu> getViewMenuItems() {
        Menu menu = null;

        if (stack instanceof Deck) {
            final Deck deck = (Deck) stack;
            if (menu == null) {
                menu = new Menu("Deck");
            }

            final MenuItem shuffle = new MenuItem("Shuffle");
            shuffle.setOnAction((event) -> {
                deck.shuffle();
            });
            menu.getItems().add(shuffle);

            final Menu refresh = new Menu("Refresh from");
            context.getDiscards().forEach((target) -> {
                final MenuItem menuItem = new MenuItem(target.getName());
                menuItem.setOnAction((event) -> {
                    deck.shuffleAndRefresh(target);
                });
                refresh.getItems().add(menuItem);
            });
            menu.getItems().add(refresh);
        }

        return Optional.ofNullable(menu);
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
