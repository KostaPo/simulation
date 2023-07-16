package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Predator;
import ru.kostapo.service.EntityServiceImpl;

abstract class PredatorSpawnAction extends SpawnAction<Predator> {

    protected Predator createEntity(WorldMap worldMap) {
        return new Predator(scale, worldMap.getEmptyCoordinate(),
                Config.getCreaturesSpeed(worldMap.getMapSize()), new EntityServiceImpl(worldMap));
    }

}
