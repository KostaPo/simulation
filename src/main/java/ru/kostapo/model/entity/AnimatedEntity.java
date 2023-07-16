package ru.kostapo.model.entity;

import ru.kostapo.model.enums.EntityType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AnimatedEntity extends Entity {
    protected int animationIndex =0;
    protected List<ArrayList<Image>> animationImgList = new ArrayList<>();
    public abstract void initAnimationSprites();
    public void updateAnimation() {
        this.animationIndex++;
        if(animationIndex > animationImgList.size()-1)
            animationIndex = 0;
        this.sprite = animationImgList.get(0).get(animationIndex);
    }
    @Override
    public EntityType getType() {
        return EntityType.ANIMATED;
    }
}
