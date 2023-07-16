package ru.kostapo.actions.spawn;

import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Fire;

public class FireSpawn extends FireSpawnAction {

    public FireSpawn(final int scale) {
        this.scale = scale;
    }

    @Override
    protected Fire createEntity(WorldMap worldMap) {
        return null;
    }

    @Override
    public void reSpawn(WorldMap worldMap) {
    }
}
