package ru.kostapo.actions.spawn;

import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Predator;

public class PredatorSpawn extends PredatorSpawnAction {

    public PredatorSpawn(final int scale) {
        this.scale = scale;
    }

    @Override
    public void reSpawn(WorldMap worldMap) {
        Predator p = createEntity(worldMap);
        worldMap.addEntity(p.getBoardPoint(), p);
    }

}
