// package com.goodformentertainment.tool.card;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
//
// import org.junit.Test;
//
// import com.goodformentertainment.tool.card.model.Card;
// import com.goodformentertainment.tool.card.model.Deck;
// import com.goodformentertainment.tool.card.model.Discard;
// import com.goodformentertainment.tool.card.model.Game;
// import com.goodformentertainment.tool.card.model.Player;
//
// public class CardsTest {
// @Test
// public void test() {
// final Game game = new Game();
// final Player todd = new Player("Todd");
// final Player tory = new Player("Tory");
// game.addPlayer(todd);
// game.addPlayer(tory);
//
// final Deck deck = createPokerDeck();
// deck.shuffle();
//
// final Discard discard = new Discard();
//
// for (final Player player : game.getPlayers()) {
// player.getHand().draw(deck, 5);
// System.out.println(player + ": " + player.getHand());
// }
//
// System.out.println("Deck remaining: " + deck.size());
// System.out.println("Discards: " + discard);
//
// final Optional<Card> card = deck.take();
// if (card.isPresent()) {
// System.out.println(card);
// }
// }
//
// private Deck createLetterDeck() {
// final Deck deck = new Deck();
// deck.add(new Card("A"));
// deck.add(new Card("B"));
// deck.add(new Card("C"));
// deck.add(new Card("D"));
// deck.add(new Card("E"));
// deck.add(new Card("F"));
// deck.add(new Card("G"));
// deck.add(new Card("H"));
// deck.add(new Card("I"));
// deck.add(new Card("J"));
// deck.add(new Card("K"));
// deck.add(new Card("L"));
// deck.add(new Card("M"));
// deck.add(new Card("N"));
// deck.add(new Card("O"));
// deck.add(new Card("P"));
// deck.add(new Card("Q"));
// deck.add(new Card("R"));
// deck.add(new Card("S"));
// deck.add(new Card("T"));
// deck.add(new Card("U"));
// deck.add(new Card("V"));
// deck.add(new Card("W"));
// deck.add(new Card("X"));
// deck.add(new Card("Y"));
// deck.add(new Card("Z"));
// return deck;
// }
//
// private Deck createPokerDeck() {
// final Deck deck = new Deck();
//
// final List<String> suits = new ArrayList<>();
// suits.add("Spades");
// suits.add("Hearts");
// suits.add("Clubs");
// suits.add("Diamonds");
//
// final List<String> numbers = new ArrayList<>();
// numbers.add("Two");
// numbers.add("Three");
// numbers.add("Four");
// numbers.add("Five");
// numbers.add("Six");
// numbers.add("Seven");
// numbers.add("Eight");
// numbers.add("Nine");
// numbers.add("Ten");
// numbers.add("Jack");
// numbers.add("Queen");
// numbers.add("King");
// numbers.add("Ace");
//
// for (final String suit : suits) {
// for (final String number : numbers) {
// deck.add(new Card(number + " of " + suit));
// }
// }
//
// return deck;
// }
// }
