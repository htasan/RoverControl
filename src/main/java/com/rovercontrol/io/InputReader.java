package com.rovercontrol.io;

import com.rovercontrol.enums.Command;
import com.rovercontrol.enums.Direction;
import com.rovercontrol.exception.InputReaderException;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class InputReader {

    public Plateau createPlateau(String fileName) throws InputReaderException {
        Plateau plateau = new Plateau();
        Scanner in = createFileScanner(fileName);
        readPlateauProperties(plateau, in);
        readRovers(plateau, in);
        return plateau;
    }

    private Scanner createFileScanner(String fileName) throws InputReaderException {
        try {
            URL url = getClass().getClassLoader().getResource(fileName);
            File file = new File(url.getPath());
            return new Scanner(file);
        } catch (Exception e) {
            throw new InputReaderException(String.format("%s could not be found in resources", fileName), e);
        }
    }

    private void readPlateauProperties(Plateau plateau, Scanner in) throws InputReaderException {
        try {
            plateau.setLength(in.nextInt());
            plateau.setWidth(in.nextInt());
        } catch (Exception e) {
            throw new InputReaderException("Plateau properties could not be read", e);
        }
    }

    private void readRovers(Plateau plateau, Scanner in) throws InputReaderException {
        int id = 0;
        while (in.hasNext()) {
            readRover(plateau, in, ++id);
        }
    }

    private void readRover(Plateau plateau, Scanner in, int id) throws InputReaderException {
        Rover rover;
        try {
            rover = new Rover(id, in.nextInt(), in.nextInt(), Direction.valueOf(in.next()));
        } catch (Exception e) {
            throw new InputReaderException(String.format("Position info for Rover%d could not be read", id), e);
        }
        readCommands(rover, in);
        plateau.addRover(rover);
    }

    private void readCommands(Rover rover, Scanner in) throws InputReaderException {
        try {
            in.nextLine();
            String commands = in.nextLine();
            for(char command : commands.toCharArray()) {
                rover.addCommand(Command.valueOf(Character.toString(command)));
            }
        } catch (Exception e) {
            throw new InputReaderException(String.format("Command info for %s could not be read", rover.getName()), e);
        }
    }
}
