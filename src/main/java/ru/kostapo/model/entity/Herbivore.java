package ru.kostapo.model.entity;

import ru.kostapo.model.common.Coordinates;
import ru.kostapo.service.EntityService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static ru.kostapo.Config.*;

public class Herbivore extends Creature {

    public Herbivore(int mapScale, Coordinates coordinates, int hSpeed, EntityService eService) {
        speed = hSpeed;
        hp = HERBIVORE_HP;
        attackPower = HERBIVORE_ATTACK_POWER;
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
    public BufferedImage getImageAsset() {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entity/green_sprite.png"));
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, "can't load herbivore spritesheet");
            e.printStackTrace();
        }
        return spriteSheet;
    }

    @Override
    void doStep() {
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
