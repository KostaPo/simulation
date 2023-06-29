package ru.kostapo.view;

import java.awt.*;

public class GBC extends GridBagConstraints {

    //**строка и столбец в левом верхнем углу компонента.*/
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    //** Количество столбцов (для gridwidth) или строк (для gridheight) в области отображения компонента.*/
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this(gridx, gridy);
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    //** Используется, когда компонент меньше области отображения,
    // чтобы определить, где (внутри области) разместить компонент.
    // По умолчанию CENTER*/
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    //** Используется, когда область отображения компонента больше запрошенного размера компонента,
    // чтобы определить, следует ли и как изменять размер компонента.
    // По умолчанию NONE.*/
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    //** Используются для определения того, как распределять пространство между столбцами (weightx) и между строками (weighty),
     // задаются крайними значениями 0,0 и 1,0 большие числа указывают на то,
     // что строка или столбец компонента должны занимать больше места.*/
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    //** Задает минимальное расстояние между компонентом и краями его области отображения.
    // По умолчанию null*/
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    //** Задает размеры компонента*/
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
