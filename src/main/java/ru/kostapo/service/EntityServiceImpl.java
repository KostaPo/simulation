package ru.kostapo.service;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.common.Node;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.model.entity.Entity;

import java.util.*;
public class EntityServiceImpl implements EntityService {

    private final WorldMap worldMap;

    public EntityServiceImpl(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public <T extends Entity> List<T> findAllTargets(Coordinates current, Class<T> targetType) {
        List<T> targetsList = worldMap.getEntitiesOfType(targetType);
        targetsList.sort((first, second) -> {
            int distant1 = getDistanceToTarget(current, first.getBoardPoint());
            int distant2 = getDistanceToTarget(current, second.getBoardPoint());
            if (distant1 > distant2) {
                return 1;
            } else if (distant1 < distant2) {
                return -1;
            } else {
                return 0;
            }
        });
        return targetsList;
    }

    @Override
    public <T extends Entity> LinkedList<Coordinates> findPath(Coordinates startPoint, T target) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        Node start = new Node(startPoint);
        start.setF(start.getG() + getDistanceToTarget(start.getCoordinates(), target.getBoardPoint()));
        openList.add(start);
        while (!openList.isEmpty()) {
            Node node = openList.peek();
            if (node.getCoordinates().equals(target.getBoardPoint()))
                return getPathFromResultNode(node);
            for (Node n : node.getNextStepsNodes(worldMap)) {
                if (isStepOrTarget(n.getCoordinates(), target.getClass())) {
                    if (!closedList.contains(n)) {
                        n.setParent(node);
                        n.setG(n.getG()+1);
                        n.setH(getDistanceToTarget(n.getCoordinates(), target.getBoardPoint()));
                        n.setF(n.getG() + n.getH());
                        openList.add(n);
                    }
                }
            }
            openList.remove(node);
            closedList.add(node);
        }
        return null;
    }

    private  <T> boolean isStepOrTarget(Coordinates stepPoint, Class<T> targetType) {
        if(!worldMap.isEmpty(stepPoint))
            return targetType.isInstance(worldMap.getEntity(stepPoint));
        return true;
    }

    private LinkedList<Coordinates> getPathFromResultNode(Node targetNode) {
        Node node = targetNode;
        LinkedList<Coordinates> coordinates = new LinkedList<>();
        while (node.getParent() != null) {
            coordinates.add(new Coordinates(node.getX(), node.getY()));
            node = node.getParent();
        }
        Collections.reverse(coordinates);
        return coordinates;
    }

    @Override
    public <T extends Entity> boolean isTargetOnPoint(T target) {
        if(!worldMap.isEmpty(target.getBoardPoint()))
            return worldMap.getEntity(target.getBoardPoint()) == target;
        return false;
    }

    @Override
    public int getDistanceToTarget(Coordinates current, Coordinates target) {
        return Math.abs(current.getX() - target.getX()) +
                Math.abs(current.getY() - target.getY());
    }

    @Override
    public boolean isStepPossible(Coordinates step) {
        return worldMap.isEmpty(step);
    }

    @Override
    public void doStep(Entity entity, Coordinates newBoardPoint) {
        worldMap.removeEntity(entity.getBoardPoint());
        worldMap.addEntity(newBoardPoint, entity);
    }

    @Override
    public void attackTarget(Entity target, int attackPower) {
        target.attacked(attackPower);
    }
}
