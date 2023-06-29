package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.enums.EntityType;

import java.awt.*;

public abstract class Entity {
    protected int hp;
    protected boolean death;
    protected Image sprite;
    protected int scale;
    protected Coordinates boardPoint;
    protected Coordinates spriteCoordinates;
    public abstract void attacked(int attackPower);
    public Image getSprite() {
        return this.sprite;
    }
    public Coordinates getBoardPoint() {
        return this.boardPoint;
    }
    public Coordinates getSpriteCoordinates() {
        return this.spriteCoordinates;
    }
    public boolean isDeath() {
        return this.death;
    }
    public EntityType getType() {
        return EntityType.STATIC;
    }
}
