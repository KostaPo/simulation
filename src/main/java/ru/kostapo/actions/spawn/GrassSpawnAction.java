package ru.kostapo.actions.spawn;

import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Grass;

abstract class GrassSpawnAction extends SpawnAction<Grass> {

    protected Grass createEntity(WorldMap worldMap) {
        return new Grass(scale, worldMap.getEmptyCoordinate());
    }

}
