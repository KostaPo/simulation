package ru.kostapo.actions.init;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InitFireSpritesheet implements Action {
    @Override
    public void doAction(WorldMap worldMap) {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entity/fire_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load fire spritesheet");
            e.printStackTrace();
        }
        worldMap.setFireSpritesheet(spriteSheet);
    }
}
