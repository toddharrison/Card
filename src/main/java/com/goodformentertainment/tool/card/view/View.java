package com.goodformentertainment.tool.card.view;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.goodformentertainment.tool.event.EventListener;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;

public abstract class View<T> implements EventListener {
    private Optional<View<?>> parent;
    private Pane pane;

    public View() {
        parent = Optional.empty();
    }

    public abstract T getModel();

    public Pane getPane() {
        return pane;
    }

    public void setPane(final Pane pane) {
        this.pane = pane;
        setViewContextMenu();
    }

    public Optional<View<?>> getParent() {
        return parent;
    }

    public void setParent(final View<?> parent) {
        this.parent = Optional.of(parent);
        setViewContextMenu();
    }

    public void removeParent() {
        parent = Optional.empty();
    }

    public List<MenuItem> getViewMenuItems() {
        return Collections.emptyList();
    }

    protected Optional<ContextMenu> getContextMenu() {
        Optional<ContextMenu> menu = Optional.empty();
        if (parent.isPresent()) {
            menu = parent.get().getContextMenu();
        }

        final List<MenuItem> viewMenuItems = getViewMenuItems();
        if (viewMenuItems != null && !viewMenuItems.isEmpty()) {
            if (menu.isPresent()) {
                menu.get().getItems().add(new SeparatorMenuItem());
            } else {
                menu = Optional.of(new ContextMenu());
            }
            menu.get().getItems().addAll(viewMenuItems);
        }

        return menu;
    }

    private void setViewContextMenu() {
        final Optional<ContextMenu> menu = getContextMenu();
        if (menu.isPresent()) {
            pane.setOnMousePressed((event) -> {
                if (event.isSecondaryButtonDown()) {
                    menu.get().show(pane, event.getScreenX(), event.getScreenY());
                    event.consume();
                }
            });
        }
    }
}
