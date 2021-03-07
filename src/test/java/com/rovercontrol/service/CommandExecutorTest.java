package com.rovercontrol.service;

import com.rovercontrol.enums.Command;
import com.rovercontrol.enums.Direction;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {

    @InjectMocks
    private CommandExecutor commandExecutor;

    @Mock
    private RoverController roverController;

    @Test
    public void shouldExecuteTurnLeftCommand() {
        Plateau plateau = new Plateau();
        Rover rover = new Rover(1, 1, 1, Direction.N);
        rover.addCommand(Command.L);
        plateau.addRover(rover);

        commandExecutor.moveRovers(plateau);

        verify(roverController).turnLeft(rover);
    }

    @Test
    public void shouldExecuteTurnRightCommand() {
        Plateau plateau = new Plateau();
        Rover rover = new Rover(1, 1, 1, Direction.N);
        rover.addCommand(Command.R);
        plateau.addRover(rover);

        commandExecutor.moveRovers(plateau);

        verify(roverController).turnRight(rover);
    }

    @Test
    public void shouldExecuteMoveForwardCommand() {
        Plateau plateau = new Plateau();
        Rover rover = new Rover(1, 1, 1, Direction.N);
        rover.addCommand(Command.M);
        plateau.addRover(rover);

        commandExecutor.moveRovers(plateau);

        verify(roverController).moveForward(rover, plateau);
    }

    @Test
    public void shouldExecuteMultipleCommandsInOrder() {
        Plateau plateau = new Plateau();
        Rover rover1 = new Rover(1, 1, 1, Direction.N);
        rover1.addCommand(Command.L);
        rover1.addCommand(Command.M);
        Rover rover2 = new Rover(2, 2, 1, Direction.E);
        rover2.addCommand(Command.M);
        rover2.addCommand(Command.R);
        rover2.addCommand(Command.M);
        plateau.addRover(rover1);
        plateau.addRover(rover2);

        commandExecutor.moveRovers(plateau);

        InOrder inOrder = Mockito.inOrder(roverController);
        inOrder.verify(roverController).turnLeft(rover1);
        inOrder.verify(roverController).moveForward(rover1, plateau);
        inOrder.verify(roverController).moveForward(rover2, plateau);
        inOrder.verify(roverController).turnRight(rover2);
        inOrder.verify(roverController).moveForward(rover2, plateau);
    }
}