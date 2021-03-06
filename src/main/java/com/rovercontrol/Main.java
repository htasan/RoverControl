package com.rovercontrol;

import com.rovercontrol.exception.InputReaderException;
import com.rovercontrol.io.InputReader;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.service.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            Plateau plateau = new InputReader().createPlateau(FILE_NAME);
            new CommandExecutor().moveRovers(plateau);
            plateau.getRoverList().forEach(rover -> LOGGER.info(rover.getInfo()));
        } catch (InputReaderException ire) {
            LOGGER.error(String.format("Error while reading %s", FILE_NAME), ire);
        }
    }
}
