package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.service.EntityService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static ru.kostapo.Config.*;

public class Predator extends Creature {

    public Predator(int mapScale, Coordinates coordinates, int pSpeed, EntityService eService) {
        speed = pSpeed;
        hp = PREDATOR_HP;
        attackPower = PREDATOR_ATTACK_POWER;
        scale = mapScale;
        service = eService;
        boardPoint = coordinates;
        spriteCoordinates = new Coordinates(
                boardPoint.getX() * scale,
                boardPoint.getY() * scale);
        initAnimationSprites();
    }

    @Override
    void initTargetTracking() {
        target = null;
        pathToTarget = null;
        List<Herbivore> targets = service.findAllTargets(boardPoint, Herbivore.class);
        for (Herbivore herbivore : targets) {
            target = herbivore;
            pathToTarget = service.findPath(boardPoint, target);
            if (pathToTarget != null) {
                break;
            }
        }
    }

    @Override
    public BufferedImage getImageAsset() {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entity/red_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load predator spritesheet");
            e.printStackTrace();
        }
        return spriteSheet;
    }

    @Override
    void doStep() {
        if (service.isTargetOnPoint(target)) {
            Coordinates nextStepPoint = pathToTarget.pollFirst();
            if (nextStepPoint.equals(target.boardPoint)) {
                attack(target);
            } else {
                if (service.isStepPossible(nextStepPoint)) {
                    stepCount++;
                    service.doStep(this, nextStepPoint);
                    boardPoint = nextStepPoint;
                }
            }
        }
        initTargetTracking();
        if (stepCount % STEPS_WITHOUT_HUNGER == 0)
            attacked(PREDATOR_HUNGER);
    }
}
