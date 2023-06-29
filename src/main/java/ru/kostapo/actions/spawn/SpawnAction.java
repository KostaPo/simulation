package ru.kostapo.actions.spawn;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

public abstract class SpawnAction implements Action {
    protected abstract void initSpawn(WorldMap worldMap);
}
