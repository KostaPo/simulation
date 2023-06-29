package ru.kostapo.actions.init;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InitPredatorSpritesheet implements Action {
    @Override
    public void doAction(WorldMap worldMap) {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entity/red_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load predator spritesheet");
            e.printStackTrace();
        }
        worldMap.setPredatorSpritesheet(spriteSheet);
    }
}
