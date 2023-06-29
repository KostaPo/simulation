package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.entity.Herbivore;
import ru.kostapo.service.EntityService;
import ru.kostapo.service.EntityServiceImpl;

public class HerbivoreSpawn extends SpawnAction {

    @Override
    public void doAction(WorldMap worldMap) {
        initSpawn(worldMap);
    }

    @Override
    protected void initSpawn(WorldMap worldMap) {
        EntityService service = new EntityServiceImpl(worldMap);
        for (int i = 0; i < Config.getSpawnRate(worldMap.getMapSize()); i++) {
            Coordinates coordinates = worldMap.getEmptyCoordinate();
            Herbivore herbivore = new Herbivore(worldMap.getScale(), coordinates,
                    Config.getCreaturesSpeed(worldMap.getMapSize()), service);
            herbivore.initAnimationSprites(worldMap.getHerbivoreSpritesheet());
            worldMap.addEntity(coordinates, herbivore);
        }
    }
}
