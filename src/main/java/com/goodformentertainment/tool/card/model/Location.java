package com.goodformentertainment.tool.card.model;

import java.awt.Point;

public class Location {
    protected Table table;
    protected Point point;

    public Location(final Table table, final Point point) {
        this.table = table;
        this.point = point;
    }

    public Table getTable() {
        return table;
    }

    public Point getPoint() {
        return point;
    }
}
