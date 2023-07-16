package ru.kostapo.actions.spawn;

import ru.kostapo.Config;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.entity.Fire;
import ru.kostapo.model.enums.FireFigure;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

abstract class FireSpawnAction extends SpawnAction<Fire> {

    @Override
    protected void initSpawn(WorldMap worldMap) {
        for (int i = 0; i < Config.getSpawnRate(worldMap.getMapSize()); i++) {
            List<Coordinates> figure = getFigureCoordinates(getRandomFigure());
            Coordinates spawnPoint = findFigureSpawnPoint(figure, worldMap);
            for (Coordinates value : figure) {
                Coordinates coordinates = new Coordinates(
                        value.getX() + spawnPoint.getX(),
                        value.getY() + spawnPoint.getY()
                );
                if (worldMap.isInBorder(coordinates)) {
                    Fire fire = new Fire(scale, coordinates);
                    worldMap.addEntity(coordinates, fire);
                }
            }
        }
    }

    private Coordinates findFigureSpawnPoint(List<Coordinates> figure, WorldMap worldMap) {
        Coordinates spawnPoint;
        while (true) {
            boolean readyToSpawn = true;
            spawnPoint = worldMap.getEmptyCoordinate();
            for (Coordinates value : figure) {
                Coordinates tmp = new Coordinates(
                        value.getX() + spawnPoint.getX(),
                        value.getY() + spawnPoint.getY());
                if (!worldMap.isInBorder(tmp)) {
                    readyToSpawn = false;
                }
                if (!worldMap.isEmpty(tmp)) {
                    readyToSpawn = false;
                }
                if (tmp.getX() == 0 || (tmp.getX() == worldMap.getMapSize() - 1)) {
                    readyToSpawn = false; // чтоб огонь не спавнился по бокам
                }
                if (tmp.getY() == 0 || (tmp.getY() == worldMap.getMapSize() - 1)) {
                    readyToSpawn = false; // чтоб огонь не спавнился по бокам
                }
            }
            if (readyToSpawn)
                return spawnPoint;
        }
    }

    private List <Coordinates> getFigureCoordinates(FireFigure fireFigure) {
        return fireFigure.getFigureCoordinates();
    }

    private FireFigure getRandomFigure() {
        List<FireFigure> fireFigures = Arrays.asList(FireFigure.values());
        return fireFigures.get(ThreadLocalRandom.current().nextInt(FireFigure.values().length));
    }
}
