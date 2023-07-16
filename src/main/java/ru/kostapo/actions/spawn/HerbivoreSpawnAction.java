package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Herbivore;
import ru.kostapo.service.EntityServiceImpl;

abstract class HerbivoreSpawnAction extends SpawnAction<Herbivore> {

    protected Herbivore createEntity(WorldMap worldMap) {
        return new Herbivore(scale, worldMap.getEmptyCoordinate(),
                Config.getCreaturesSpeed(worldMap.getMapSize()), new EntityServiceImpl(worldMap));
    }

}
