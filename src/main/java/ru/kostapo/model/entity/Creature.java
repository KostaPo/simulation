package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.enums.EntityType;
import ru.kostapo.service.EntityService;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import static ru.kostapo.Config.*;
import static ru.kostapo.Config.WALK_STATE;

public abstract class Creature extends AnimatedEntity {

    protected EntityService service;

    protected Entity target;

    protected int attackPower;

    protected int speed;

    protected int stepCount;

    protected int currentState = WALK_STATE;

    protected LinkedList<Coordinates> pathToTarget;

    public int getStepCount() {
        return this.stepCount;
    }

    public int getHp() {
        return this.hp;
    }

    public int getCurrentState() {
        return this.currentState;
    }

    abstract void doStep();

    abstract void initTargetTracking();

    public void attack(Entity target) {
        animationIndex = 0;
        currentState = ATTACK_STATE;
        service.attackTarget(target, attackPower);
        hp += attackPower;
    }

    public void attacked(int attackPower) {
        if(currentState != DEATH_STATE) {
            hp -= attackPower;
            if (hp <= 0) {
                animationIndex = 0;
                currentState = DEATH_STATE;
            }
        }
    }

    public void update() {
        if(currentState != DEATH_STATE) {
            if (target == null || pathToTarget == null) {
                currentState = IDLE_STATE;
                initTargetTracking();
            } else {
                relocateSprite();
                if (isSpriteOnBoardPoint()) {
                    doStep();
                }
            }
        }
        updateAnimation();
    }

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
    public void updateAnimation() {
        animationIndex++;
        if (animationIndex > animationImgList.get(currentState).size() - 1) {
            if(currentState == DEATH_STATE)
                death = true;
            if (currentState != WALK_STATE)
                currentState = WALK_STATE;
            animationIndex = 0;
        }
        this.sprite = animationImgList.get(currentState).get(animationIndex);
    }

    @Override
    public void initAnimationSprites() {
        BufferedImage spriteSheet = getImageAsset();
        int width_counter = spriteSheet.getTileWidth() / CREATURE_SPRITE_SIZE;
        int height_counter = spriteSheet.getTileHeight() / CREATURE_SPRITE_SIZE;
        for (int i = 0; i < height_counter; i++) {
            animationImgList.add(new ArrayList<>());
            for (int j = 0; j < width_counter; j++) {
                switch (i) {
                    case (IDLE_STATE):
                        animationImgList.get(IDLE_STATE).add(spriteSheet.getSubimage(
                                j * CREATURE_SPRITE_SIZE,
                                i * CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE));
                        break;
                    case (GESTURE_STATE):
                        animationImgList.get(GESTURE_STATE).add(spriteSheet.getSubimage(
                                j * CREATURE_SPRITE_SIZE,
                                i * CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE));
                        break;
                    case (WALK_STATE):
                        animationImgList.get(WALK_STATE).add(spriteSheet.getSubimage(
                                j * CREATURE_SPRITE_SIZE,
                                i * CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE));
                        break;
                    case (ATTACK_STATE):
                        animationImgList.get(ATTACK_STATE).add(spriteSheet.getSubimage(
                                j * CREATURE_SPRITE_SIZE,
                                i * CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE));
                        break;
                    case (DEATH_STATE):
                        animationImgList.get(DEATH_STATE).add(spriteSheet.getSubimage(
                                j * CREATURE_SPRITE_SIZE,
                                i * CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE,
                                CREATURE_SPRITE_SIZE));
                        break;
                }
            }
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.CREATURE;
    }
}
