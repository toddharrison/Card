package com.goodformentertainment.tool.card.view;

import java.util.Optional;

import com.goodformentertainment.tool.event.EventListener;

import javafx.scene.layout.Pane;

public abstract class View<T> implements EventListener {
    private Optional<View<?>> parent;

    public abstract T getModel();

    public abstract Pane getPane();

    public Optional<View<?>> getParent() {
        return parent;
    }

    public void setParent(final View<?> parent) {
        this.parent = Optional.of(parent);
    }

    public void removeParent() {
        parent = Optional.empty();
    }
}
