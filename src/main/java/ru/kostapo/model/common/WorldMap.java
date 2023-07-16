package ru.kostapo.model.common;

import ru.kostapo.model.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap {

    private final int mapSize;

    private BufferedImage background;

    private final LinkedHashMap<Coordinates, Entity> entities;

    public WorldMap(Dimension panelDimension, int mapSize) {
        this.mapSize = mapSize;
        this.entities = new LinkedHashMap<>();
        initMapBackground(mapSize, panelDimension);
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

    public int getMapSize() {
        return mapSize;
    }

    private void initMapBackground(int mapSize, Dimension dimension) {
        int scale = dimension.width/mapSize;
        Image tile = null;
        BufferedImage background = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        try {
            tile = new ImageIcon(getClass().getResource("/map/tile.png")).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load background tile");
            e.printStackTrace();
        }
        Graphics2D g = (Graphics2D) background.getGraphics();
        for (int i=0; i<mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                g.drawImage(tile, i*scale ,j*scale, scale, scale, null);
            }
        }
        this.background = background;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public LinkedHashMap<Coordinates, Entity> getEntities() {
        return entities;
    }
}
