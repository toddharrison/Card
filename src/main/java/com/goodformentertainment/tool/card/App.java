package com.goodformentertainment.tool.card;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.Game;
import com.goodformentertainment.tool.card.model.Player;
import com.goodformentertainment.tool.card.view.CardImager;
import com.goodformentertainment.tool.card.view.HandView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class);

    public static void main(final String[] args) {
        launch(args);
    }

    private final CardImager imager;

    private final Game game;
    private final Player playerTodd;
    private final Player playerTory;
    private Deck deck;

    // @FXML
    // private Text actiontarget;

    public App() {
        imager = new CardImager("images/empty.png");

        game = new Game();
        playerTodd = new Player("Todd");
        game.addPlayer(playerTodd);
        playerTory = new Player("Tory");
        game.addPlayer(playerTory);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        deck = createDeck(stage);

        deck.shuffle();

        playerTodd.getHand().draw(deck, 5);
        playerTory.getHand().draw(deck, 5);
        playerTodd.getHand().setFaceUp(false);

        // final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        final BorderPane root = new BorderPane();

        final HandView toddHandView = new HandView(imager, stage, playerTodd.getHand());
        root.setTop(toddHandView.getPane());

        final Label tableNode = new Label("Table");
        root.setCenter(tableNode);

        final HandView toryHandView = new HandView(imager, stage, playerTory.getHand());
        root.setBottom(toryHandView.getPane());

        final Scene scene = new Scene(root);
        scene.getStylesheets()
                .add(getClass().getClassLoader().getResource("app.css").toExternalForm());

        stage.setTitle("Cards");
        stage.setScene(scene);
        stage.show();
    }

    // @FXML
    // protected void handleSubmitButtonAction(final ActionEvent event) {
    // actiontarget.setText("Sign in button pressed");
    // }

    private Deck createDeck(final Stage stage) {
        final Deck deck = new Deck();

        final String type = "build";
        imager.addType(type, "images/build-back.png");
        for (int i = 1; i < 15; i++) {
            final Card card = new Card("Build " + i, type);
            imager.addCard(card.getName(), "images/build-" + i + ".png");
            deck.add(card);
        }

        return deck;
    }
}
