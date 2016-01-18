package com.goodformentertainment.tool.card.view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.layout.Pane;

public interface View<T extends Observable> extends Observer {
    T getModel();

    Pane getPane();
}
