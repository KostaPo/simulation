package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.service.EntityService;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.kostapo.Config.*;

public class Herbivore extends Creature {
    private final List<ArrayList<Image>> animationImgList;
    private int animationIndex;
    private final EntityService service;
    private Grass target;
    private LinkedList<Coordinates> pathToTarget;
    public Herbivore(int mapScale, Coordinates coordinates, int hSpeed, EntityService eService) {
        death = false;
        speed = hSpeed;
        hp = HERBIVORE_HP;
        scale = mapScale;
        service = eService;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(boardPoint.getX() * scale, boardPoint.getY() * scale);
        animationImgList = new ArrayList<>();
        currentState = WALK_STATE;
    }
    @Override
    public void initAnimationSprites(BufferedImage spriteSheet) {
        int width_counter = spriteSheet.getTileWidth() / HERBIVORE_SPRITE_SIZE;
        int height_counter = spriteSheet.getTileHeight() / HERBIVORE_SPRITE_SIZE;
        for (int i = 0; i < height_counter; i++) {
            animationImgList.add(new ArrayList<>());
            for (int j = 0; j < width_counter; j++) {
                switch (i) {
                    case (IDLE_STATE):
                        animationImgList.get(IDLE_STATE).add(spriteSheet.getSubimage(
                                j * HERBIVORE_SPRITE_SIZE,
                                i * HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE));
                        break;
                    case (GESTURE_STATE):
                        animationImgList.get(GESTURE_STATE).add(spriteSheet.getSubimage(
                                j * HERBIVORE_SPRITE_SIZE,
                                i * HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE));
                        break;
                    case (WALK_STATE):
                        animationImgList.get(WALK_STATE).add(spriteSheet.getSubimage(
                                j * HERBIVORE_SPRITE_SIZE,
                                i * HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE));
                        break;
                    case (ATTACK_STATE):
                        animationImgList.get(ATTACK_STATE).add(spriteSheet.getSubimage(
                                j * HERBIVORE_SPRITE_SIZE,
                                i * HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE));
                        break;
                    case (DEATH_STATE):
                        animationImgList.get(DEATH_STATE).add(spriteSheet.getSubimage(
                                j * HERBIVORE_SPRITE_SIZE,
                                i * HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE,
                                HERBIVORE_SPRITE_SIZE));
                        break;
                }
            }
        }
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
    void initTargetTracking() {
        target = null;
        pathToTarget = null;
        List<Grass> targets = service.findAllTargets(boardPoint, Grass.class);
        for (Grass grass : targets) {
            target = grass;
            pathToTarget = service.findPath(boardPoint, target);
            if (pathToTarget != null) {
                break;
            }
        }
    }
    @Override
    public void attack(Entity target) {
        animationIndex = 0;
        currentState = ATTACK_STATE;
        service.attackTarget(target, HERBIVORE_ATTACK_POWER);
        hp += HERBIVORE_ATTACK_POWER;
    }
    @Override
    public void attacked(int attackPower) {
        if(currentState != DEATH_STATE) {
            hp -= attackPower;
            if (hp <= 0) {
                animationIndex = 0;
                currentState = DEATH_STATE;
            }
        }
    }

    @Override
    public void makeMove() {
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

    private void doStep() {
        if(service.isTargetOnPoint(target)) {
            Coordinates nextStepPoint = pathToTarget.pollFirst();
            if (nextStepPoint.equals(target.boardPoint)) {
                attack(target);
            } else {
                if(service.isStepPossible(nextStepPoint)) {
                    stepCount++;
                    service.doStep(this, nextStepPoint);
                    boardPoint = nextStepPoint;
                }
            }
        }
        initTargetTracking();
    }
}
