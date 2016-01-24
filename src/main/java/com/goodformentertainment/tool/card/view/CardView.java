package com.goodformentertainment.tool.card.view;

import java.util.Observable;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Placeable;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CardView extends View<Card> {
    public static final int MAX_CARD_SIZE = 112;

    private static final Logger LOG = Logger.getLogger(CardView.class);

    private static final String STYLE_CARD = "card-small";
    private static final String STYLE_POPUP = "card-large";

    private static final int SIZE_SMALL = 100;
    private static final int SIZE_POPUP = 500;

    private static boolean showingPopupCard;

    private final CardImager imager;
    private final Stage stage;
    private final StackPane pane;
    private final ImageView view;

    private Card card;
    private EventHandler<MouseEvent> onClickHandler;
    private Tooltip tooltip;

    public CardView(final CardImager imager, final Stage stage) {
        this.imager = imager;
        this.stage = stage;
        view = new ImageView(imager.getEmptyImage(SIZE_SMALL));
        pane = new StackPane(view);
        pane.getStyleClass().add(STYLE_CARD);

        pane.setMaxHeight(MAX_CARD_SIZE);
        pane.setMaxWidth(MAX_CARD_SIZE);

        addContextMenu();
    }

    @Override
    public StackPane getPane() {
        return pane;
    }

    @Override
    public Card getModel() {
        return card;
    }

    public void setCard(final Card card) {
        this.card = card;
        setImageByCardFacing();
        card.addObserver(this);
    }

    public void removeCard() {
        if (card != null) {
            view.setImage(imager.getEmptyImage(SIZE_SMALL));
            card.deleteObserver(this);
            card = null;

            if (onClickHandler != null) {
                pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);
                onClickHandler = null;
            }
            if (tooltip != null) {
                Tooltip.uninstall(pane, tooltip);
            }
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        LOG.info("update: " + arg);
        if (arg instanceof Placeable.Observe) {
            switch ((Placeable.Observe) arg) {
                case FACING:
                    setImageByCardFacing();
                    break;
                case LOCATION:
                    // Do nothing
                    break;
            }
        }
    }

    private void setImageByCardFacing() {
        if (card != null) {
            final Card c = card;
            if (c.isFaceUp()) {
                view.setImage(imager.getCardImage(c.getName(), SIZE_SMALL));

                onClickHandler = (event) -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (!showingPopupCard) {
                            createPopup(c.getName()).show(stage);
                            showingPopupCard = true;
                        }
                        event.consume();
                    }
                };
                pane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);

                tooltip = new Tooltip();
                tooltip.setText(c.getName());
                Tooltip.install(pane, tooltip);
            } else {
                view.setImage(imager.getTypeImage(c.getType(), SIZE_SMALL));

                if (onClickHandler != null) {
                    pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);
                    onClickHandler = null;
                }
                if (tooltip != null) {
                    Tooltip.uninstall(pane, tooltip);
                }
            }
        }
    }

    public Popup createPopup(final String name) {
        final Popup popup = new Popup();

        final Image image = imager.getCardImage(name, SIZE_POPUP);
        final StackPane popupPane = new StackPane(new ImageView(image));
        popupPane.getStyleClass().add(STYLE_POPUP);
        popup.getContent().add(popupPane);
        popup.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                popup.hide();
                showingPopupCard = false;
                event.consume();
            }
        });

        return popup;
    }

    private void addContextMenu() {
        // final ContextMenu contextMenu = new ContextMenu();
        // if (stack instanceof Deck) {
        // final MenuItem draw = new MenuItem("Draw");
        // contextMenu.getItems().addAll(draw);
        // } else if (stack instanceof Discard) {
        // final MenuItem take = new MenuItem("Take");
        // contextMenu.getItems().addAll(take);
        // } else {
        // throw new UnsupportedOperationException(
        // "Unknown CardStack type: " + stack.getClass().getName());
        // }

        pane.setOnMousePressed((event) -> {
            if (event.isSecondaryButtonDown()) {
                // contextMenu.show(pane, event.getScreenX(), event.getScreenY());
                // event.consume();
                LOG.info("Queried for menu");
            }
        });
    }
}
