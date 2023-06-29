package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.enums.EntityType;
import ru.kostapo.service.EntityService;

import java.awt.image.BufferedImage;

import static ru.kostapo.Config.GRASS_HP;

public class Grass extends Entity {
    private final EntityService service;
    public Grass (int mapScale, Coordinates coordinates, EntityService eService) {
        death = false;
        hp = GRASS_HP;
        service = eService;
        scale = mapScale;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(
                boardPoint.getX()*scale,
                boardPoint.getY()*scale
        );
    }
    public void initSprite(BufferedImage sprite) {
        this.sprite = sprite;
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
