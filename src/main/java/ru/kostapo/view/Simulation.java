package ru.kostapo.view;

import ru.kostapo.Config;
import ru.kostapo.actions.spawn.*;
import ru.kostapo.model.common.Coordinates;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.actions.Action;
import ru.kostapo.actions.NextTurn;
import ru.kostapo.model.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static ru.kostapo.Config.FONT_SCALE;
import static ru.kostapo.Config.FPS;

public class Simulation extends JPanel implements KeyListener, Runnable {
    private final Graphics2D g1;
    private final BufferedImage bufferedImage;
    private Thread thread;
    private volatile boolean simulate;
    private final int scale;
    private final WorldMap worldMap;
    private List<Action> turnActions;

    public Simulation(Dimension dimension) {
        super();
        OptionDialog optionDialog = new OptionDialog(null);
        int mapSize = optionDialog.showOptionDialog();
        scale = dimension.width / mapSize;
        bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        g1 = (Graphics2D) bufferedImage.getGraphics();
        worldMap = new WorldMap(dimension, mapSize);
        setPreferredSize(dimension);
        setFocusable(true);
        requestFocus();
        init();
    }

    private void init() {
        LinkedHashMap<Class<? extends Entity>, SpawnAction<? extends Entity>> spawnActions = new LinkedHashMap<>();
        spawnActions.put(Fire.class, new FireSpawn(scale));
        spawnActions.put(Grass.class, new GrassSpawn(scale));
        spawnActions.put(Herbivore.class, new HerbivoreSpawn(scale));
        spawnActions.put(Predator.class, new PredatorSpawn(scale));
        for (Class<? extends Entity> entityClass : spawnActions.keySet()) {
            spawnActions.get(entityClass).doAction(worldMap);
        }
        turnActions = new ArrayList<>();
        turnActions.add(new NextTurn(spawnActions));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null)
            thread = new Thread(this);
        addKeyListener(this);
        thread.start();
    }

    @Override
    public void run() {
        final long targetTime = 1000 / FPS;
        long start;
        long elapsed;
        long wait;
        simulate = true;
        while (true) {
            start = System.nanoTime();
            if (simulate) {
                nextTurn();
                render();
            }
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0)
                wait = 5;
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "CURRENT THREAD EXCEPTION: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            this.simulate = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            this.simulate = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void nextTurn() {
        for (Action action : turnActions)
            action.doAction(worldMap);
    }

    private void render() {
        draw(g1);
        //** swing double buffering, без этого мигает белым**//
        Graphics g2 = getGraphics();
        g2.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        g1.clearRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        g2.dispose();
    }

    private void draw(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, scale / FONT_SCALE));
        g.drawImage(worldMap.getBackground(),
                0,
                0,
                worldMap.getBackground().getWidth(),
                worldMap.getBackground().getHeight(),
                null);
        for (Coordinates coordinates : worldMap.getEntities().keySet()) {
            Entity entity = worldMap.getEntities().get(coordinates);
            g.drawImage(entity.getSprite(),
                    entity.getSpriteCoordinates().getX(),
                    entity.getSpriteCoordinates().getY(),
                    scale,
                    scale,
                    null);
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if(creature.getCurrentState() != Config.DEATH_STATE) {
                    g.setColor(Color.RED);
                    g.drawString("hp: " + creature.getHp(),
                            creature.getSpriteCoordinates().getX(),
                            creature.getSpriteCoordinates().getY() + g.getFont().getSize());
                    g.setColor(Color.BLUE);
                    g.drawString("step: " + creature.getStepCount(),
                            creature.getSpriteCoordinates().getX(),
                            creature.getSpriteCoordinates().getY() + g.getFont().getSize() * 2);
                }
            }
        }
    }
}
