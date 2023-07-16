package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Entity;

public abstract class SpawnAction<T extends Entity> extends Action {

    protected int scale;

    @Override
    public void doAction(WorldMap worldMap) {
        initSpawn(worldMap);
    }

    protected void initSpawn(WorldMap worldMap) {
        for (int i = 0; i < Config.getSpawnRate(worldMap.getMapSize()); i++) {
            T entity = createEntity(worldMap);
            worldMap.addEntity(entity.getBoardPoint(), entity);
        }
    }

    protected abstract T createEntity(WorldMap worldMap);

    public abstract void reSpawn(WorldMap worldMap);
}
