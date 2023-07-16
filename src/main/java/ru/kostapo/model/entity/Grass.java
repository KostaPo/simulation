package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.enums.EntityType;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;

import static ru.kostapo.Config.GRASS_HP;

public class Grass extends Entity {

    public Grass (int mapScale, Coordinates coordinates) {
        sprite = getImageAsset();
        death = false;
        hp = GRASS_HP;
        scale = mapScale;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(
                boardPoint.getX()*scale,
                boardPoint.getY()*scale
        );
    }

    @Override
    public BufferedImage getImageAsset() {
        BufferedImage asset = null;
        try {
            asset = ImageIO.read(getClass().getResourceAsStream("/entity/grass_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load grass spritesheet");
            e.printStackTrace();
        }
        return asset;
    }

    @Override
    public void attacked(int attackPower) {
        hp-=attackPower;
        if(hp <= 0) {
            death = true;
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.STATIC;
    }
}
