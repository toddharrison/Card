package com.goodformentertainment.tool.card.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.goodformentertainment.tool.card.model.event.LengthChangeEvent;
import com.goodformentertainment.tool.card.model.event.OrderChangeEvent;
import com.goodformentertainment.tool.card.model.event.TopChangeEvent;

/**
 * Represents an ordered List of Cards with a start and an end.
 *
 * @author todd
 */
public abstract class CardList extends Placeable {
    private final LinkedList<Card> cards;

    public CardList() {
        cards = new LinkedList<>();
    }

    @Override
    public void setFaceUp(final boolean faceUp) {
        super.setFaceUp(faceUp);
        for (final Card card : cards) {
            card.setFaceUp(faceUp);
        }
    }

    @Override
    public void place(final Location location) {
        super.place(location);
        for (final Card card : cards) {
            card.place(location);
        }
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Adds a Card to the end of this List.
     *
     * @param card
     */
    public void add(final Card card) {
        card.setFaceUp(isFaceUp());
        cards.add(card);
        dispatch(new LengthChangeEvent());
    }

    /**
     * Adds a List of Cards to the end of this List, preserving the List order.
     *
     * @param cards
     */
    public void add(final List<Card> cards) {
        if (!cards.isEmpty()) {
            for (final Card card : cards) {
                card.setFaceUp(isFaceUp());
                this.cards.add(card);
            }
            dispatch(new LengthChangeEvent());
        }
    }

    /**
     * Adds a list of Cards to the end of this List, preserving the list order.
     *
     * @param cards
     */
    public void add(final Card... cards) {
        for (final Card card : cards) {
            card.setFaceUp(isFaceUp());
            this.cards.add(card);
        }
        dispatch(new LengthChangeEvent());
    }

    /**
     * Adds a Card to the start of this List.
     *
     * @param card
     */
    public void addToTop(final Card card) {
        card.setFaceUp(isFaceUp());
        cards.push(card);
        dispatch(new LengthChangeEvent());
        dispatch(new TopChangeEvent());
    }

    /**
     * Adds a List of Cards to the start of this List, preserving the List order.
     *
     * @param cards
     */
    public void addToTop(final List<Card> cards) {
        // Add in reverse to preserve List order
        final ListIterator<Card> iterator = cards.listIterator(cards.size());
        while (iterator.hasPrevious()) {
            final Card card = iterator.previous();
            card.setFaceUp(isFaceUp());
            this.cards.push(card);
        }
        dispatch(new LengthChangeEvent());
        dispatch(new TopChangeEvent());
    }

    /**
     * Takes a Card, if available, from the start of this List.
     *
     * @return
     */
    public Optional<Card> take() {
        final Card card = cards.removeFirst();
        dispatch(new LengthChangeEvent());
        dispatch(new TopChangeEvent());
        return Optional.ofNullable(card);
    }

    /**
     * Takes the specified number of Cards, up to the number available, from the start of this List.
     *
     * @param count
     * @return
     */
    public List<Card> take(final int count) {
        final List<Card> takenCards = new LinkedList<>();
        while (!cards.isEmpty() && takenCards.size() < count) {
            takenCards.add(cards.removeFirst());
        }
        dispatch(new LengthChangeEvent());
        dispatch(new TopChangeEvent());
        return takenCards;
    }

    /**
     * Takes the specified Card from this List.
     *
     * @param card
     * @return
     */
    public Card take(final Card card) {
        if (cards.remove(card)) {
            dispatch(new LengthChangeEvent());
            dispatch(new TopChangeEvent());
            return card;
        } else {
            throw new IllegalArgumentException("That Card is not in this List");
        }
    }

    /**
     * Takes all the remaining Cards in this List.
     *
     * @return
     */
    public List<Card> takeAll() {
        final List<Card> removedCards = new LinkedList<>(cards);
        cards.clear();
        dispatch(new LengthChangeEvent());
        dispatch(new TopChangeEvent());
        return removedCards;
    }

    /**
     * Takes a random Card, if available, from this List.
     *
     * @return
     */
    public Optional<Card> takeRandom() {
        Card card = null;
        if (cards.size() > 0) {
            final int index = ThreadLocalRandom.current().nextInt(cards.size());
            card = cards.remove(index);
            dispatch(new LengthChangeEvent());
            if (index == 0) {
                dispatch(new TopChangeEvent());
            }
        }
        return Optional.ofNullable(card);
    }

    /**
     * Takes the specified number of Cards, up to the number available, at random from this List.
     *
     * @param count
     * @return
     */
    public List<Card> takeRandom(final int count) {
        final List<Card> takenCards = new LinkedList<>();
        boolean removedFirst = false;
        while (!cards.isEmpty() && takenCards.size() < count) {
            final int index = ThreadLocalRandom.current().nextInt(cards.size());
            takenCards.add(cards.remove(index));
            if (index == 0) {
                removedFirst = true;
            }
        }
        dispatch(new LengthChangeEvent());
        if (removedFirst) {
            dispatch(new TopChangeEvent());
        }
        return takenCards;
    }

    public boolean has(final Card card) {
        return cards.contains(card);
    }

    /**
     * Returns the number of Cards in this List.
     *
     * @return
     */
    public int size() {
        return cards.size();
    }

    /**
     * Shuffles all the Cards into random order in this List.
     */
    public void shuffle() {
        Collections.shuffle(cards);
        dispatch(new TopChangeEvent());
        dispatch(new OrderChangeEvent());
    }

    /**
     * Sorts the Cards in this List using the specified Comparator.
     *
     * @param comparator
     */
    public void sort(final Comparator<Card> comparator) {
        cards.sort(comparator);
        dispatch(new TopChangeEvent());
        dispatch(new OrderChangeEvent());
    }

    /**
     * Reverses the order of the Cards in this List.
     */
    public void reverse() {
        Collections.reverse(cards);
        dispatch(new TopChangeEvent());
        dispatch(new OrderChangeEvent());
    }

    /**
     * View the top Card, if available, of this Stack.
     *
     * @return
     */
    public Optional<Card> viewTop() {
        return Optional.ofNullable(cards.peek());
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
