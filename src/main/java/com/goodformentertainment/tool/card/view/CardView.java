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

public class CardView implements View<Card> {
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
                view.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);
                onClickHandler = null;
            }
            if (tooltip != null) {
                Tooltip.uninstall(view, tooltip);
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
                    throw new UnsupportedOperationException();
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
                view.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);

                tooltip = new Tooltip();
                tooltip.setText(c.getName());
                Tooltip.install(view, tooltip);
            } else {
                view.setImage(imager.getTypeImage(c.getType(), SIZE_SMALL));

                if (onClickHandler != null) {
                    view.removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickHandler);
                    onClickHandler = null;
                }
                if (tooltip != null) {
                    Tooltip.uninstall(view, tooltip);
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
}
