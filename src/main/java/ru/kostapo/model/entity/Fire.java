package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Fire extends AnimatedEntity {
    private final static int SPRITE_SIZE = 96;
    private final List<Image> animationImgList;
    private int animationIndex;
    public Fire (int mapScale, Coordinates coordinates) {
        death = false;
        scale = mapScale;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(boardPoint.getX()*scale, boardPoint.getY()*scale);
        animationImgList = new ArrayList<>();
    }
    @Override
    public void initAnimationSprites(BufferedImage spriteSheet) {
        int width_counter = spriteSheet.getTileWidth() / SPRITE_SIZE;
        int height_counter = spriteSheet.getTileHeight() / SPRITE_SIZE;
        for (int i = 0; i < width_counter; i++) {
            for (int j = 0; j < height_counter; j++) {
                animationImgList.add(spriteSheet.getSubimage(i * SPRITE_SIZE, j * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE));
            }
        }
        animationImgList.remove(animationImgList.size()-1);
    }

    @Override
    public void attacked(int attackPower) {
        System.out.println("WTF!?" + attackPower);
    }

    @Override
    public void updateAnimation() {
        animationIndex++;
        if(animationIndex > animationImgList.size()-1)
            animationIndex = 0;
        this.sprite = animationImgList.get(animationIndex);
    }
}
