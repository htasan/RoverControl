package com.rovercontrol.service;

import com.rovercontrol.enums.Direction;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;

import java.util.List;
import java.util.stream.Collectors;

public class RoverController {

    public void turnLeft(Rover rover) {
        if (rover.isDead()) {
            return;
        }
        Direction[] directions = Direction.values();
        rover.setDir(directions[(rover.getDir().ordinal() + directions.length - 1) % directions.length]);
    }

    public void turnRight(Rover rover) {
        if (rover.isDead()) {
            return;
        }
        Direction[] directions = Direction.values();
        rover.setDir(directions[(rover.getDir().ordinal() + 1) % directions.length]);
    }

    public void moveForward(Rover rover, Plateau plateau) {
        if (rover.isDead()) {
            return;
        }
        move(rover);
        controlInPlateau(rover, plateau.getLength(), plateau.getWidth());
        if (rover.isDead()) {
            return;
        }
        controlCrash(rover, plateau.getRoverList());
        if (rover.isDead()) {
            return;
        }
        rover.setInfo(rover.getName() + " is on position " + rover.getPosition());
    }

    private void move(Rover rover) {
        rover.setX(rover.getX() + rover.getDir().getX());
        rover.setY(rover.getY()  + rover.getDir().getY());
    }

    private void controlInPlateau(Rover rover, int plateauLength, int plateauWidth) {
        if (isInPlateau(rover, plateauLength, plateauWidth)) {
            return;
        }
        rover.setInfo(rover.getName() + " has gone off plateau. Last seen on position " + rover.getPreviousPosition());
        rover.setDead(true);
    }

    private boolean isInPlateau(Rover rover, int plateauLength, int plateauWidth) {
        return rover.getX() >= 0 && rover.getX() <= plateauLength && rover.getY() >= 0 && rover.getY() <= plateauWidth;
    }

    private void controlCrash(Rover rover, List<Rover> roverList) {
        List<Rover> crashedRovers = roverList.stream()
                .filter(otherRover -> !rover.equals(otherRover) && rover.onSamePosition(otherRover))
                .collect(Collectors.toList());
        if (crashedRovers.isEmpty()) {
            return;
        }
        setCrashInfo(rover, crashedRovers);
    }

    private void setCrashInfo(Rover rover, List<Rover> crashedRovers) {
        rover.setInfo(rover.getName()
                + " has crashed " + crashedRovers.stream().map(Rover::getName).collect(Collectors.joining(", "))
                + " on position " + rover.getPosition());
        rover.setDead(true);
        crashedRovers.forEach(otherRover -> {
            if (otherRover.isDead()) {
                return;
            }
            otherRover.setInfo(otherRover.getName()
                    + " got hit by " + rover.getName()
                    + " while standing on position " + otherRover.getPosition());
            otherRover.setDead(true);
        });
    }
}
