package ru.kostapo.actions.init;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InitHerbivoreSpritesheet implements Action {
    @Override
    public void doAction(WorldMap worldMap) {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entity/green_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load herbivore spritesheet");
            e.printStackTrace();
        }
        worldMap.setHerbivoreSpritesheet(spriteSheet);
    }
}
