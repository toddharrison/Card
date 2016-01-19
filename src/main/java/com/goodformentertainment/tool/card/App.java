package com.goodformentertainment.tool.card;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.Discard;
import com.goodformentertainment.tool.card.model.Game;
import com.goodformentertainment.tool.card.model.Player;
import com.goodformentertainment.tool.card.model.Table;
import com.goodformentertainment.tool.card.view.CardImager;
import com.goodformentertainment.tool.card.view.HandView;
import com.goodformentertainment.tool.card.view.TableView;
import com.goodformentertainment.tool.card.view.TableView.Position;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class);

    public static void main(final String[] args) {
        launch(args);
    }

    private final CardImager imager;

    private final Game game;
    private final Player northPlayer;
    private final Player southPlayer;
    private Table table;
    private Deck deck;
    private Discard discard;

    // @FXML
    // private Text actiontarget;

    public App() {
        imager = new CardImager("images/empty.png");

        game = new Game();
        northPlayer = new Player("North");
        game.addPlayer(northPlayer);
        southPlayer = new Player("South");
        game.addPlayer(southPlayer);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        table = new Table();
        deck = createDeck(stage);
        discard = new Discard();
        table.place(deck);
        table.place(discard);

        deck.shuffle();

        northPlayer.getHand().draw(deck, 3);
        southPlayer.getHand().draw(deck, 3);
        northPlayer.getHand().setFaceUp(false);

        northPlayer.getMeld().place(deck.take(2));
        northPlayer.getMeld().setFaceUp(true);
        southPlayer.getMeld().place(deck.take(1));
        southPlayer.getMeld().setFaceUp(true);

        // final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        final BorderPane root = new BorderPane();

        final HandView toddHandView = new HandView(imager, stage, northPlayer.getHand());
        root.setTop(toddHandView.getPane());

        final TableView tableView = new TableView(imager, stage, table);
        tableView.setMeld(northPlayer.getMeld(), Position.NORTH);
        tableView.setMeld(southPlayer.getMeld(), Position.SOUTH);
        root.setCenter(tableView.getPane());

        final HandView toryHandView = new HandView(imager, stage, southPlayer.getHand());
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
