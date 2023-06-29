package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.entity.Predator;
import ru.kostapo.service.EntityService;
import ru.kostapo.service.EntityServiceImpl;

public class PredatorSpawn extends SpawnAction {

    @Override
    public void doAction(WorldMap worldMap) {
        initSpawn(worldMap);
    }
    @Override
    protected void initSpawn(WorldMap worldMap) {
        EntityService service = new EntityServiceImpl(worldMap);
        for (int i = 0; i < Config.getSpawnRate(worldMap.getMapSize()); i++) {
            Coordinates coordinates = worldMap.getEmptyCoordinate();
            Predator predator = new Predator(worldMap.getScale(), coordinates,
                    Config.getCreaturesSpeed(worldMap.getMapSize()), service);
            predator.initAnimationSprites(worldMap.getPredatorSpritesheet());
            worldMap.addEntity(coordinates, predator);
        }
    }
}
