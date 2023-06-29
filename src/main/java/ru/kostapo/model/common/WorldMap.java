package ru.kostapo.model.common;

import ru.kostapo.Config;
import ru.kostapo.model.entity.*;
import ru.kostapo.model.enums.EntityType;
import ru.kostapo.service.EntityServiceImpl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.kostapo.Config.FONT_SCALE;

public class WorldMap {

    //**для экономии памяти храним картинки в единственном экземпляре, в WorldMap, а не в каждом Entity**//
    private BufferedImage background;
    private BufferedImage herbivoreSpritesheet;
    private BufferedImage predatorSpritesheet;
    private BufferedImage fireSpritesheet;
    private BufferedImage grassSprite;
    private final int scale;
    private final int mapSize;
    private final Font textFont;
    private final Dimension panelDimension;
    private final LinkedHashMap<Coordinates, Entity> entities;

    public WorldMap(Dimension panelDimension, int mapSize) {
        this.panelDimension = panelDimension;
        this.mapSize = mapSize;
        this.scale = panelDimension.width / mapSize;
        this.entities = new LinkedHashMap<>();
        this.textFont = new Font("Arial", Font.BOLD, scale / FONT_SCALE);
    }

    public void update() {
        Map<Coordinates, Entity> tmp = new LinkedHashMap<>(entities);
        Iterator<Coordinates> itr = tmp.keySet().iterator();
        while (itr.hasNext()) {
            Coordinates coordinates = itr.next();
            Entity entity = entities.get(coordinates);
            if(entity.isDeath()) {
                removeEntity(coordinates);
                reSpawn(entity);
            } else {
                if (entity.getType().equals(EntityType.ANIMATED))
                    ((AnimatedEntity) entity).updateAnimation();
                if (entity.getType().equals(EntityType.CREATURE))
                    ((Creature) entity).makeMove();
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setFont(textFont);
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        for (Coordinates coordinates : entities.keySet()) {
            Entity entity = entities.get(coordinates);
            g.drawImage(entity.getSprite(),
                    entity.getSpriteCoordinates().getX(),
                    entity.getSpriteCoordinates().getY(),
                    scale,
                    scale,
                    null);
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if(creature.getCurrentState() != Config.DEATH_STATE) {
                    g.setColor(Color.RED);
                    g.drawString("hp: " + creature.getHp(),
                            creature.getSpriteCoordinates().getX(),
                            creature.getSpriteCoordinates().getY() + textFont.getSize());
                    g.setColor(Color.BLUE);
                    g.drawString("step: " + creature.getStepCount(),
                            creature.getSpriteCoordinates().getX(),
                            creature.getSpriteCoordinates().getY() + textFont.getSize() * 2);
                }
            }
        }
    }

    public void reSpawn(Entity entity) {
        if (entity instanceof Grass) {
            Grass g = new Grass(scale, getEmptyCoordinate(), new EntityServiceImpl(this));
            g.initSprite(grassSprite);
            addEntity(g.getBoardPoint(), g);
        }
        if (entity instanceof Herbivore) {
            Herbivore h = new Herbivore(scale, getEmptyCoordinate(), Config.getCreaturesSpeed(mapSize), new EntityServiceImpl(this));
            h.initAnimationSprites(herbivoreSpritesheet);
            addEntity(h.getBoardPoint(), h);
        }
        if (entity instanceof Predator) {
            Predator p = new Predator(scale, getEmptyCoordinate(), Config.getCreaturesSpeed(mapSize), new EntityServiceImpl(this));
            p.initAnimationSprites(predatorSpritesheet);
            addEntity(p.getBoardPoint(), p);
        }
    }

    public Coordinates getEmptyCoordinate() {
        while (true) {
            Coordinates coordinates = new Coordinates(
                    ThreadLocalRandom.current().nextInt(mapSize),
                    ThreadLocalRandom.current().nextInt(mapSize));
            if (isEmpty(coordinates))
                return coordinates;
        }
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public void removeEntity(Coordinates boardPoint) {
        entities.remove(boardPoint);
    }

    public boolean isEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public boolean isInBorder(Coordinates coordinates) {
        if ((coordinates.getX() >= mapSize || coordinates.getX() < 0))
            return false;
        if ((coordinates.getY() >= mapSize || coordinates.getY() < 0))
            return false;
        return true;
    }

    public <T> List<T> getEntitiesOfType(Class<T> type) {
        List<T> list = new LinkedList<>();
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (type.isInstance(entry.getValue())) {
                list.add(type.cast(entry.getValue()));
            }
        }
        return list;
    }

    public int getScale() {
        return scale;
    }

    public int getMapSize() {
        return mapSize;
    }

    public Dimension getPanelDimension() {
        return panelDimension;
    }

    public void setBackground(BufferedImage backgroundSprite) {
        background = backgroundSprite;
    }

    public void setHerbivoreSpritesheet(BufferedImage spritesheet) {
        herbivoreSpritesheet = spritesheet;
    }

    public BufferedImage getHerbivoreSpritesheet() {
        return herbivoreSpritesheet;
    }

    public void setPredatorSpritesheet(BufferedImage spritesheet) {
        predatorSpritesheet = spritesheet;
    }

    public BufferedImage getPredatorSpritesheet() {
        return predatorSpritesheet;
    }

    public void setFireSpritesheet(BufferedImage spritesheet) {
        fireSpritesheet = spritesheet;
    }

    public BufferedImage getFireSpritesheet() {
        return fireSpritesheet;
    }

    public void setGrassSprite(BufferedImage spritesheet) {
        grassSprite = spritesheet;
    }

    public BufferedImage getGrassSprite() {
        return grassSprite;
    }
}
