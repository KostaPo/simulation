package ru.kostapo.actions.init;

import ru.kostapo.actions.Action;
import ru.kostapo.model.common.WorldMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InitGrassSprite implements Action {
    @Override
    public void doAction(WorldMap worldMap) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/entity/grass_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load grass spritesheet");
            e.printStackTrace();
        }
        worldMap.setGrassSprite(sprite);
    }
}
