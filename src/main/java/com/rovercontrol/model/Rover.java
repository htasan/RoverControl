package com.rovercontrol.model;

import com.rovercontrol.enums.Command;
import com.rovercontrol.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class Rover {

    private final int id;
    private int x;
    private int y;
    private Direction dir;
    private final List<Command> commandList = new ArrayList<>();
    private boolean isDead;
    private String info;

    public Rover(int id, int x, int y, Direction dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return "Rover" + id;
    }

    public String getPosition() {
        return x + " " + y + " " + dir;
    }

    public String getPreviousPosition() {
        return (x - dir.getX()) + " " + (y - dir.getY()) + " " + dir;
    }

    public boolean onSamePosition(Rover rover) {
        return x == rover.getX() && y == rover.getY();
    }
}
