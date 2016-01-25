package com.goodformentertainment.tool.card;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.model.Card;
import com.goodformentertainment.tool.card.model.Deck;
import com.goodformentertainment.tool.card.model.Discard;
import com.goodformentertainment.tool.card.model.Game;
import com.goodformentertainment.tool.card.model.Player;
import com.goodformentertainment.tool.card.model.Table;
import com.goodformentertainment.tool.card.view.CardImager;
import com.goodformentertainment.tool.card.view.GameView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class);

    public static void main(final String[] args) {
        launch(args);
    }

    private final CardImager imager;

    private final Game game;
    private final Player toddPlayer;
    private final Player toryPlayer;

    // @FXML
    // private Text actiontarget;

    public App() {
        imager = new CardImager("images/empty.png");

        game = new Game();
        toddPlayer = new Player("Todd");
        game.addPlayer(toddPlayer);
        toryPlayer = new Player("Tory");
        game.addPlayer(toryPlayer);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final GameView gameView = new GameView(imager, stage, game);

        final Table table = game.getTable();
        final Deck deck = createDeck(stage);
        final Discard discard = new Discard();
        table.place(deck);
        table.place(discard);

        deck.shuffle();

        final Card card = deck.take().get();
        discard.add(card);
        LOG.info(card);
        LOG.info(deck.getCards());
        LOG.info(discard.getCards());

        toddPlayer.getHand().draw(deck, 3);
        toryPlayer.getHand().draw(deck, 3);
        toddPlayer.getHand().setFaceUp(false);

        toddPlayer.getMeld().place(deck.take(2));
        toddPlayer.getMeld().setFaceUp(true);
        toryPlayer.getMeld().place(deck.take(1));
        toryPlayer.getMeld().setFaceUp(true);

        // final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        final Scene scene = new Scene(gameView.getPane());
        scene.getStylesheets()
                .add(getClass().getClassLoader().getResource("app.css").toExternalForm());

        stage.setTitle("Cards");
        stage.setScene(scene);
        stage.show();

        // LOG.info(((Pane) tableView.getPane().getTop()).getHeight());
        // LOG.info(((Pane) tableView.getPane().getCenter()).getHeight());
        // LOG.info(((Pane) ((BorderPane) root.getCenter()).getCenter()).getHeight());
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
