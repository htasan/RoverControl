package com.rovercontrol.io;

import com.rovercontrol.enums.Command;
import com.rovercontrol.enums.Direction;
import com.rovercontrol.exception.InputRuleException;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;

import java.io.InputStream;
import java.util.Scanner;

public class InputReader {

    public Plateau createPlateau(String fileName) throws InputRuleException {
        Plateau plateau = new Plateau();
        Scanner in = createFileScanner(fileName);
        readPlateauProperties(plateau, in);
        readRovers(plateau, in);
        return plateau;
    }

    private Scanner createFileScanner(String fileName) throws InputRuleException {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            return new Scanner(inputStream);
        } catch (Exception e) {
            throw new InputRuleException(String.format("%s could not be found in resources", fileName), e);
        }
    }

    private void readPlateauProperties(Plateau plateau, Scanner in) throws InputRuleException {
        int length;
        int width;
        try {
            length = in.nextInt();
            width = in.nextInt();
        } catch (Exception e) {
            throw new InputRuleException("Plateau properties could not be read", e);
        }
        if (length < 0 || width < 0) {
            throw new InputRuleException("Both dimensions of plateau should be positive");
        }
        plateau.setLength(length);
        plateau.setWidth(width);
    }

    private void readRovers(Plateau plateau, Scanner in) throws InputRuleException {
        int id = 0;
        while (in.hasNext()) {
            readRover(plateau, in, ++id);
        }
    }

    private void readRover(Plateau plateau, Scanner in, int id) throws InputRuleException {
        Rover rover;
        try {
            rover = new Rover(id, in.nextInt(), in.nextInt(), Direction.valueOf(in.next()));
        } catch (Exception e) {
            throw new InputRuleException(String.format("Position info for Rover%d could not be read", id), e);
        }
        if (!rover.isInPlateau(plateau.getLength(), plateau.getWidth())) {
            throw new InputRuleException(String.format("%s cannot be positioned on %s, which is out of plateau", rover.getName(), rover.getPosition()));
        }
        if (plateau.getRoverList().stream().anyMatch(rover::onSamePosition)) {
            throw new InputRuleException(String.format("%s cannot be positioned on %s because there is another rover there", rover.getName(), rover.getPosition()));
        }
        readCommands(rover, in);
        plateau.addRover(rover);
    }

    private void readCommands(Rover rover, Scanner in) throws InputRuleException {
        try {
            in.nextLine();
            String commands = in.nextLine();
            for(char command : commands.toCharArray()) {
                rover.addCommand(Command.valueOf(Character.toString(command)));
            }
        } catch (Exception e) {
            throw new InputRuleException(String.format("Command info for %s could not be read", rover.getName()), e);
        }
    }
}
