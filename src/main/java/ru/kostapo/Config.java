package ru.kostapo;

public class Config {

    //** ⚠️изменение настроек не гарантирует корректную работу ⚠️**//
    public static final int FPS = 24;
    public static final int FONT_SCALE = 4;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public static final int IDLE_STATE = 0;
    public static final int GESTURE_STATE = 1;
    public static final int WALK_STATE = 2;
    public static final int ATTACK_STATE = 3;
    public static final int DEATH_STATE = 4;

    public static final int GRASS_HP = 1;

    public static final int CREATURE_SPRITE_SIZE = 32;

    public static final int PREDATOR_HP = 5;
    public static final int PREDATOR_ATTACK_POWER = 2;
    public static final int STEPS_WITHOUT_HUNGER = 3;
    public static final int PREDATOR_HUNGER = 2;

    public static final int HERBIVORE_HP = 5;
    public static final int HERBIVORE_ATTACK_POWER = 1;

    public static int getSpawnRate(int mapSize) {
        if(mapSize == 5)
            return 2;
        if(mapSize == 10)
            return 5;
        if(mapSize == 15)
            return 10;
        return 1;
    }

    public static int getCreaturesSpeed(int mapSize) {
        if(mapSize == 5)
            return 3;
        if(mapSize == 10)
            return 2;
        return 1;
    }
}
