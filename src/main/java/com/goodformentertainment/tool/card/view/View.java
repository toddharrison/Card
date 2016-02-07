package com.goodformentertainment.tool.card.view;

import java.util.Optional;

import com.goodformentertainment.tool.card.model.Context;
import com.goodformentertainment.tool.event.EventListener;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;

public abstract class View<T> implements EventListener {
    protected final Context context;

    private Optional<View<?>> parent;
    private Pane pane;

    public View(final Context context) {
        this.context = context;
        parent = Optional.empty();
    }

    public abstract T getModel();

    public Pane getPane() {
        return pane;
    }

    public void setPane(final Pane pane) {
        this.pane = pane;
        updateContextMenu();
    }

    public Optional<View<?>> getParent() {
        return parent;
    }

    public void setParent(final View<?> parent) {
        this.parent = Optional.of(parent);
        updateContextMenu();
    }

    public void removeParent() {
        parent = Optional.empty();
    }

    public Optional<Menu> getViewMenuItems() {
        return Optional.empty();
    }

    public void updateContextMenu() {
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

    private Optional<ContextMenu> getContextMenu() {
        Optional<ContextMenu> menu = Optional.empty();
        if (parent.isPresent()) {
            menu = parent.get().getContextMenu();
        }

        final Optional<Menu> itemMenu = getViewMenuItems();
        if (itemMenu.isPresent()) {
            if (!menu.isPresent()) {
                menu = Optional.of(new ContextMenu());
            }
            menu.get().getItems().add(itemMenu.get());
        }

        return menu;
    }
}
