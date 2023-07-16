package ru.kostapo.actions.spawn;

import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Herbivore;

public class HerbivoreSpawn extends HerbivoreSpawnAction {

    public HerbivoreSpawn(final int scale) {
        this.scale = scale;
    }

    @Override
    public void reSpawn(WorldMap worldMap) {
        Herbivore h = createEntity(worldMap);
        worldMap.addEntity(h.getBoardPoint(), h);
    }

}
