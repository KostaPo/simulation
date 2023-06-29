package ru.kostapo.model.common;

import java.util.ArrayList;
import java.util.List;

public class Node extends Coordinates implements Comparable<Node> {
    private int f; // стоимость
    private int g; // перемещения из start
    private int h; // путь до цели
    private Node parent;
    public Node(Coordinates coordinates) {
        super(coordinates.getX(), coordinates.getY());
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.f, node.f);
    }

    public Coordinates getCoordinates() {
        return new Coordinates(this.getX(), this.getY());
    }
    public int getG() {
        return this.g;
    }
    public void setG(int newG) {
        this.g = newG;
    }
    public void setF(int newF) {
        this.f = newF;
    }
    public void setH(int newH) {
        this.h = newH;
    }
    public int getH(){
        return this.h;
    }
    public void setParent (Node parent) {
        this.parent = parent;
    }
    public Node getParent () {
        return this.parent;
    }
    public List<Node> getNextStepsNodes(WorldMap worldMap){
        List<Node> list = new ArrayList<>();

        Coordinates up = new Coordinates(this.getX(), this.getY()-1);
        if(worldMap.isInBorder(up))
            list.add(new Node(up));

        Coordinates down = new Coordinates(this.getX(), this.getY()+1);
        if(worldMap.isInBorder(down))
            list.add(new Node(down));

        Coordinates left = new Coordinates(this.getX()-1, this.getY());
        if(worldMap.isInBorder(left))
            list.add(new Node(left));

        Coordinates right = new Coordinates(this.getX()+1, this.getY());
        if(worldMap.isInBorder(right))
            list.add(new Node(right));

        return list;
    }
}
