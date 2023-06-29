package ru.kostapo;

import ru.kostapo.view.Simulation;

import javax.swing.*;
import java.awt.*;

import static ru.kostapo.Config.HEIGHT;
import static ru.kostapo.Config.WIDTH;


public class Main {
    public static void main(String[] args) {

        int width = WIDTH;
        int height = HEIGHT;

        JFrame jFrame = new JFrame("Simulation v2.0 by KostaPo");
        jFrame.setContentPane(new Simulation(new Dimension(width, height)));
        jFrame.setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width  - jFrame.getSize().width) / 2 - width/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - jFrame.getSize().height) / 2 - height/2);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}