package ru.kostapo.view;

import ru.kostapo.actions.init.*;
import ru.kostapo.actions.spawn.PredatorSpawn;
import ru.kostapo.model.common.WorldMap;
import ru.kostapo.actions.Action;
import ru.kostapo.actions.spawn.FireSpawn;
import ru.kostapo.actions.spawn.GrassSpawn;
import ru.kostapo.actions.spawn.HerbivoreSpawn;
import ru.kostapo.actions.turn.NextTurn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static ru.kostapo.Config.FPS;

public class Simulation extends JPanel implements KeyListener, Runnable {
    private final Graphics2D g1;
    private final BufferedImage bufferedImage;
    private Thread thread;
    private volatile boolean simulate;
    private final int mapSize;
    private final Dimension panelDimension;
    private volatile WorldMap worldMap;
    private List<Action> initActions;
    private List<Action> turnActions;
    public Simulation(Dimension dimension) {
        super();
        OptionDialog optionDialog = new OptionDialog(null);
        this.mapSize = optionDialog.showOptionDialog();
        this.panelDimension = dimension;
        setPreferredSize(dimension);
        setFocusable(true);
        requestFocus();
        bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        g1 = (Graphics2D) bufferedImage.getGraphics();
        init();
    }
    private void init() {
        worldMap = new WorldMap(panelDimension, mapSize);
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
        loadActions();
        for (Action action : initActions)
            action.doAction(worldMap);
    }
    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null)
            thread = new Thread(this);
        addKeyListener(this);
        thread.start();
    }
    @Override
    public void run() {
        final long targetTime = 1000/FPS;
        long start;
        long elapsed;
        long wait;
        simulate = true;
        while(true) {
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
        if(key==KeyEvent.VK_SPACE) {
            pauseSimulation();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE) {
            startSimulation();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    private void startSimulation(){
        this.simulate = true;
    }
    private void pauseSimulation(){
        this.simulate = false;
    }
    private void nextTurn() {
        for (Action action : turnActions)
            action.doAction(worldMap);
    }
    private void render() {
        worldMap.draw(g1);
        //** swing double buffering, без этого мигает белым иногда**//
        Graphics g2 = getGraphics();
        g2.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        g1.clearRect(0,0,getPreferredSize().width, getPreferredSize().height);
        g2.dispose();
    }
    private void loadActions() {
        initActions.add(new InitMapBackground());
        initActions.add(new InitFireSpritesheet());
        initActions.add(new FireSpawn());
        initActions.add(new InitGrassSprite());
        initActions.add(new GrassSpawn());
        initActions.add(new InitHerbivoreSpritesheet());
        initActions.add(new HerbivoreSpawn());
        initActions.add(new InitPredatorSpritesheet());
        initActions.add(new PredatorSpawn());
        turnActions.add(new NextTurn());
    }
}
