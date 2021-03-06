package com.rovercontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private int length;
    private int width;
    private final List<Rover> roverList = new ArrayList<>();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Rover> getRoverList() {
        return roverList;
    }

    public void addRover(Rover rover) {
        roverList.add(rover);
    }
}
