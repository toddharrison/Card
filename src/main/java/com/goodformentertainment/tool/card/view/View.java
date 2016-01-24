package com.goodformentertainment.tool.card.view;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.scene.layout.Pane;

public abstract class View<T extends Observable> implements Observer {
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
