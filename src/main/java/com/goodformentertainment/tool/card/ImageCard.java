// package com.goodformentertainment.tool.card;
//
// import com.goodformentertainment.tool.card.model.Card;
// import com.goodformentertainment.tool.card.util.ImageUtil;
//
// import javafx.scene.control.Tooltip;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.StackPane;
// import javafx.stage.Popup;
// import javafx.stage.Stage;
//
// public class ImageCard extends Card {
// private static final String STYLE_CARD = "card-small";
// private static final String STYLE_POPUP = "card-large";
//
// private static final int SIZE_SMALL = 100;
// private static final int SIZE_POPUP = 500;
//
// private static boolean showingPopupCard;
//
// private final Image image;
// private Image smallImage;
// private Image popupImage;
//
// public ImageCard(final String name, final String imageUrl) {
// super(name);
// image = new Image(imageUrl);
// }
//
// public Pane createPane(final Stage stage) {
// final ImageView iv = new ImageView();
// iv.setImage(getSmallImage());
//
// iv.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
// if (!showingPopupCard) {
// createPopup().show(stage);
// showingPopupCard = true;
// }
// event.consume();
// });
//
// final Tooltip tooltip = new Tooltip();
// tooltip.setText(getName());
// Tooltip.install(iv, tooltip);
//
// final StackPane pane = new StackPane(iv);
// pane.getStyleClass().add(STYLE_CARD);
//
// return pane;
// }
//
// public Popup createPopup() {
// final Popup popup = new Popup();
//
// final StackPane popupPane = new StackPane(new ImageView(getPopupImage()));
// popupPane.getStyleClass().add(STYLE_POPUP);
// popup.getContent().add(popupPane);
// popup.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
// popup.hide();
// showingPopupCard = false;
// event.consume();
// });
//
// return popup;
// }
//
// private Image getSmallImage() {
// if (smallImage == null) {
// smallImage = ImageUtil.resizeImage(image, SIZE_SMALL, SIZE_SMALL);
// }
// return smallImage;
// }
//
// private Image getPopupImage() {
// if (popupImage == null) {
// popupImage = ImageUtil.resizeImage(image, SIZE_POPUP, SIZE_POPUP);
// }
// return popupImage;
// }
// }
