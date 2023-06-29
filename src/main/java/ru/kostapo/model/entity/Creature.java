package ru.kostapo.model.entity;

import ru.kostapo.model.enums.EntityType;
public abstract class Creature extends AnimatedEntity {
    protected int speed;
    protected int stepCount;
    protected int currentState;
    public abstract void makeMove();
    abstract void initTargetTracking();
    public int getStepCount() {
        return this.stepCount;
    }
    public int getHp() {
        return this.hp;
    }

    public int getCurrentState() {
        return this.currentState;
    }
    public abstract void attack(Entity target);
    protected void relocateSprite() {
        if(spriteCoordinates.getY() > boardPoint.getY() * scale)
            spriteCoordinates.setY(spriteCoordinates.getY() - speed);
        if(spriteCoordinates.getY() < boardPoint.getY() * scale)
            spriteCoordinates.setY(spriteCoordinates.getY() + speed);
        if(spriteCoordinates.getX() > boardPoint.getX() * scale)
            spriteCoordinates.setX(spriteCoordinates.getX() - speed);
        if(spriteCoordinates.getX() < boardPoint.getX() * scale)
            spriteCoordinates.setX(spriteCoordinates.getX() + speed);
    }
    protected boolean isSpriteOnBoardPoint(){
        if((boardPoint.getX() * scale > spriteCoordinates.getX()) || (boardPoint.getY() * scale > spriteCoordinates.getY()))
            return boardPoint.getX() * scale - spriteCoordinates.getX() > 0 && boardPoint.getY() * scale - spriteCoordinates.getY() > 0;
        if((boardPoint.getX() * scale < spriteCoordinates.getX()) || (boardPoint.getY() * scale < spriteCoordinates.getY()))
            return boardPoint.getX() * scale - spriteCoordinates.getX() < 0 && boardPoint.getY() * scale - spriteCoordinates.getY() < 0;
        return true;
    }
    @Override
    public EntityType getType() {
        return EntityType.CREATURE;
    }
}
