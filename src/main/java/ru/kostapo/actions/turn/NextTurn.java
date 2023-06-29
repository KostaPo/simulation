package ru.kostapo.actions.turn;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

public class NextTurn implements Action {
    @Override
    public void doAction(WorldMap worldMap) {
        worldMap.update();
    }
}
