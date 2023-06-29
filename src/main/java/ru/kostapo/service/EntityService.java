package ru.kostapo.service;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.entity.Entity;

import java.util.LinkedList;
import java.util.List;

public interface EntityService {
    <T extends Entity> List<T> findAllTargets(Coordinates current, Class<T> targetType);
    <T extends Entity> LinkedList<Coordinates> findPath(Coordinates start, T target);
    <T extends Entity> boolean isTargetOnPoint(T target);
    int getDistanceToTarget(Coordinates current, Coordinates target);
    boolean isStepPossible(Coordinates nextStep);
    void doStep(Entity entity, Coordinates boardPoint);
    void attackTarget(Entity target, int attackPower);
}
