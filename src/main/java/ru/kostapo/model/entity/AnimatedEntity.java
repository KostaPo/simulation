package ru.kostapo.model.entity;

import ru.kostapo.model.enums.EntityType;

import java.awt.image.BufferedImage;

public abstract class AnimatedEntity extends Entity {
    public abstract void initAnimationSprites(BufferedImage spriteSheet);
    public abstract void updateAnimation();
    @Override
    public EntityType getType() {
        return EntityType.ANIMATED;
    }
}
