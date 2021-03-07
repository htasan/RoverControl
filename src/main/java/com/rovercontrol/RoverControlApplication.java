package com.rovercontrol;

import com.rovercontrol.exception.InputRuleException;
import com.rovercontrol.io.InputReader;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.service.CommandExecutor;
import com.rovercontrol.service.RoverController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            Plateau plateau = new InputReader().createPlateau(INPUT_FILE_NAME);
            new CommandExecutor(new RoverController()).moveRovers(plateau);
            plateau.getRoverList().forEach(rover -> LOGGER.info(rover.getInfo()));
        } catch (InputRuleException ire) {
            LOGGER.error(String.format("Error while reading %s", INPUT_FILE_NAME), ire);
        }
    }
}
