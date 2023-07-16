package ru.kostapo.actions.spawn;

import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Grass;

public class GrassSpawn extends GrassSpawnAction {

    public GrassSpawn(final int scale) {
        this.scale = scale;
    }

    @Override
    public void reSpawn(WorldMap worldMap) {
        Grass g = createEntity(worldMap);
        worldMap.addEntity(g.getBoardPoint(), g);
    }

}
