package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Grass;
import ru.kostapo.service.EntityService;
import ru.kostapo.service.EntityServiceImpl;

import static ru.kostapo.Config.SPAWN_RATE;

public class GrassSpawn extends SpawnAction {

    @Override
    public void doAction(WorldMap worldMap) {
        initSpawn(worldMap);
    }
    @Override
    protected void initSpawn(WorldMap worldMap) {
        EntityService service = new EntityServiceImpl(worldMap);
        for (int i = 0; i < Config.getSpawnRate(worldMap.getMapSize()); i++) {
            Coordinates coordinates = worldMap.getEmptyCoordinate();
            Grass grass = new Grass(worldMap.getScale(), coordinates, service);
            grass.initSprite(worldMap.getGrassSprite());
            worldMap.addEntity(coordinates, grass);
        }
    }
}
