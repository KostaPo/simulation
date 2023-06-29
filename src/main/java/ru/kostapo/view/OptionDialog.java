package ru.kostapo.view;

import javax.swing.*;
import java.awt.*;

public class OptionDialog extends JDialog {

    JLabel label;
    JComboBox<Integer> box;
    JButton jButton;

    public OptionDialog(JFrame frame) {
        super(frame, true);
        setUndecorated(true);
        init();
    }

    public void init() {
        Integer[] items = {5, 10, 15};
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        label = new JLabel("УКАЖИТЕ РАЗМЕР ПОЛЯ");
        add(label, new GBC(0, 0)
                .setAnchor(GridBagConstraints.CENTER)
                .setInsets(5)
                .setWeight(1, 1)
                .setFill(GridBagConstraints.CENTER)
        );

        box = new JComboBox<>(items);
        add(box, new GBC(0, 1)
                .setAnchor(GridBagConstraints.CENTER)
                .setInsets(5)
                .setWeight(1, 1)
                .setFill(GridBagConstraints.BOTH)
        );


        jButton = new JButton("СТАРТ");
        jButton.addActionListener(e -> {
                    setVisible(false);
                    dispose();
                });
        add(jButton, new GBC(0, 2, 2, 1)
                .setAnchor(GridBagConstraints.CENTER)
                .setInsets(5)
                .setWeight(1, 1)
                .setFill(GridBagConstraints.HORIZONTAL)
        );

        setSize(250, 100);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    public int showOptionDialog() {
        setVisible(true);
        return (int)box.getSelectedItem();
    }
}