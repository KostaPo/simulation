package ru.kostapo.model.enums;

import ru.kostapo.model.common.Coordinates;

import java.util.ArrayList;
import java.util.List;

public enum FireFigure {

    SINGLE(0,0),
    H_LINE(0,0, 1,0),
    V_LINE(0,0, 0,1),
    CROSS(0,0, 1,1);

    private final List<Coordinates> figure;

    FireFigure(int...points) {
        figure = new ArrayList<>();
        for (int i = 0; i < points.length; i+=2)
            figure.add(new Coordinates(points[i], points[i+1]));
    }

    public List<Coordinates> getFigureCoordinates() {
        return figure;
    }
}
