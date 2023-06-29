package ru.kostapo.actions.init;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InitMapBackground implements Action {

    @Override
    public void doAction(WorldMap worldMap) {
        Dimension dimension = worldMap.getPanelDimension();
        int mapSize = worldMap.getMapSize();
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
        worldMap.setBackground(background);
    }
}
