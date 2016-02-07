package com.goodformentertainment.tool.card.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Context;
import com.goodformentertainment.tool.card.model.Hand;
import com.goodformentertainment.tool.card.model.event.ChangeLengthEvent;
import com.goodformentertainment.tool.card.model.event.ChangeOrderEvent;
import com.goodformentertainment.tool.event.HandleEvent;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HandView extends View<Hand> {
    private static final Logger LOG = Logger.getLogger(HandView.class);

    private final CardImager imager;
    private final Stage stage;
    private final Hand hand;
    private final GridPane pane;
    private final FlowPane cardsPane;
    private final List<CardView> views;

    public HandView(final Context context, final CardImager imager, final Stage stage,
            final Hand hand) {
        super(context);
        this.imager = imager;
        this.stage = stage;
        this.hand = hand;
        views = new LinkedList<>();
        hand.register(this);

        cardsPane = new FlowPane();
        // cardsPane.setPrefWrapLength(500);
        cardsPane.getStyleClass().add("hand");
        cardsPane.setOrientation(Orientation.HORIZONTAL);
        cardsPane.setAlignment(Pos.CENTER);
        // cardsPane.setPadding(new Insets(10));
        cardsPane.setHgap(5);
        cardsPane.setVgap(5);

        cardsPane.setMinHeight(CardView.MAX_CARD_SIZE);
        cardsPane.setMinWidth(CardView.MAX_CARD_SIZE);

        final Label label = new Label(hand.getPlayer().getName());
        label.setPadding(new Insets(0, 0, 5, 0));

        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.add(label, 0, 0);
        pane.add(cardsPane, 0, 1);

        pane.widthProperty().addListener((value, oldWidth, newWidth) -> {
            cardsPane.setPrefWrapLength(newWidth.doubleValue());
        });

        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(cardsPane, HPos.CENTER);

        setPane(pane);
        updateCards();
    }

    @Override
    public Hand getModel() {
        return hand;
    }

    @HandleEvent(type = ChangeLengthEvent.class)
    public void on(final ChangeLengthEvent event) {
        updateCards();
    }

    @HandleEvent(type = ChangeOrderEvent.class)
    public void on(final ChangeOrderEvent event) {
        updateCards();
    }

    @Override
    public Optional<Menu> getViewMenuItems() {
        final Menu menu = new Menu("Hand");

        final MenuItem showHide = new MenuItem("Show/Hide");
        showHide.setOnAction((event) -> {
            if (hand.isFaceUp()) {
                hand.setFaceUp(false);
            } else {
                hand.setFaceUp(true);
            }
        });
        menu.getItems().add(showHide);

        final Menu draw = new Menu("Draw from");
        context.getDecks().forEach((target) -> {
            final MenuItem menuItem = new MenuItem(target.getName());
            menuItem.setOnAction((event) -> {
                hand.draw(target);
            });
            draw.getItems().add(menuItem);
        });
        menu.getItems().add(draw);

        return Optional.of(menu);
    }

    private void updateCards() {
        cardsPane.getChildren().clear();
        for (final View<?> view : views) {
            view.removeParent();
        }
        views.clear();

        for (final Card card : hand.getCards()) {
            final CardView view = new CardView(context, imager, stage);
            view.setParent(this);
            view.setCard(card);
            cardsPane.getChildren().add(view.getPane());
            views.add(view);
        }
    }
}
