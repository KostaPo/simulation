package ru.kostapo.actions;

import ru.kostapo.actions.spawn.SpawnAction;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.AnimatedEntity;
import ru.kostapo.model.entity.Creature;
import ru.kostapo.model.entity.Entity;
import ru.kostapo.model.enums.EntityType;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class NextTurn extends Action {

    private final LinkedHashMap<Class<? extends Entity>, SpawnAction<? extends Entity>> spawnActions;

    public NextTurn(LinkedHashMap<Class<? extends Entity>, SpawnAction<? extends Entity>> spawnActions) {
        this.spawnActions = spawnActions;
    }

    @Override
    public void doAction(WorldMap worldMap) {
        Map<Coordinates, Entity> tmp = new LinkedHashMap<>(worldMap.getEntities());
        Iterator<Coordinates> itr = tmp.keySet().iterator();
        while (itr.hasNext()) {
            Coordinates coordinates = itr.next();
            Entity entity = worldMap.getEntities().get(coordinates);
            if(entity.isDeath()) {
                worldMap.removeEntity(coordinates);
                spawnActions.get(entity.getClass()).reSpawn(worldMap);
            } else {
                if (entity.getType().equals(EntityType.ANIMATED))
                    ((AnimatedEntity) entity).updateAnimation();
                if (entity.getType().equals(EntityType.CREATURE))
                    ((Creature) entity).update();
            }
        }
    }
}
