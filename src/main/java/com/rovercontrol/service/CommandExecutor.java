package com.rovercontrol.service;

import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;

public class CommandExecutor {

    public void moveRovers(Plateau plateau) {
        plateau.getRoverList().forEach(rover -> moveRover(rover, plateau));
    }

    private void moveRover(Rover rover, Plateau plateau) {
        RoverController roverController = new RoverController();
        rover.getCommandList().forEach(command -> {
            switch (command) {
                case L:
                    roverController.turnLeft(rover);
                    break;
                case R:
                    roverController.turnRight(rover);
                    break;
                default:
                    roverController.moveForward(rover, plateau);
                    break;
            }
        });
    }
}
