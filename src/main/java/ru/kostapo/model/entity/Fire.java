package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fire extends AnimatedEntity {

    private final int SPRITE_SIZE = 96;

    public Fire (int mapScale, Coordinates coordinates) {
        death = false;
        scale = mapScale;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(
                boardPoint.getX()*scale,
                boardPoint.getY()*scale);
        animationImgList = new ArrayList<>();
        initAnimationSprites();
    }
    @Override
    public void initAnimationSprites() {
        BufferedImage spriteSheet = getImageAsset();
        int width_counter = spriteSheet.getTileWidth() / SPRITE_SIZE;
        int height_counter = spriteSheet.getTileHeight() / SPRITE_SIZE;
        for (int i = 0; i < width_counter; i++) {
            animationImgList.add(new ArrayList<>());
            for (int j = 0; j < height_counter; j++) {
                animationImgList.get(0).add(spriteSheet.getSubimage(i * SPRITE_SIZE, j * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE));
            }
        }
        animationImgList.get(0).remove(animationImgList.size()-1);
    }

    @Override
    public void attacked(int attackPower) {
        System.out.println("WTF!?" + attackPower);
    }

    @Override
    public BufferedImage getImageAsset() {
        BufferedImage asset = null;
        try {
            asset = ImageIO.read(getClass().getResourceAsStream("/entity/fire_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load fire spritesheet");
            e.printStackTrace();
        }
        return asset;
    }
}
