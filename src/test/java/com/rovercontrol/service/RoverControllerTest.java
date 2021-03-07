package com.rovercontrol.service;

import com.rovercontrol.enums.Direction;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoverControllerTest {

    private RoverController roverController;

    @Before
    public void setUp() {
        roverController = new RoverController();
    }

    @Test
    public void shouldTurnLeft() {
        Rover rover = new Rover(1, 0, 0, Direction.N);
        roverController.turnLeft(rover);
        assertEquals("0 0 W", rover.getPosition());
        roverController.turnLeft(rover);
        assertEquals("0 0 S", rover.getPosition());
        roverController.turnLeft(rover);
        assertEquals("0 0 E", rover.getPosition());
        roverController.turnLeft(rover);
        assertEquals("0 0 N", rover.getPosition());
    }

    @Test
    public void shouldNotTurnLeftWhenDead() {
        Rover rover = new Rover(1, 0, 0, Direction.N);
        rover.setDead(true);
        roverController.turnLeft(rover);
        assertEquals("0 0 N", rover.getPosition());
    }

    @Test
    public void shouldTurnRight() {
        Rover rover = new Rover(1, 0, 0, Direction.N);
        roverController.turnRight(rover);
        assertEquals("0 0 E", rover.getPosition());
        roverController.turnRight(rover);
        assertEquals("0 0 S", rover.getPosition());
        roverController.turnRight(rover);
        assertEquals("0 0 W", rover.getPosition());
        roverController.turnRight(rover);
        assertEquals("0 0 N", rover.getPosition());
    }

    @Test
    public void shouldNotTurnRightWhenDead() {
        Rover rover = new Rover(1, 0, 0, Direction.N);
        rover.setDead(true);
        roverController.turnRight(rover);
        assertEquals("0 0 N", rover.getPosition());
    }

    @Test
    public void shouldMoveForward() {
        Rover rover = new Rover(1, 2, 2, Direction.S);
        Plateau plateau = createPlateau(rover);

        roverController.moveForward(rover, plateau);

        assertEquals("2 1 S", rover.getPosition());
        assertFalse(rover.isDead());
        assertEquals("Rover1 is on position 2 1 S" , rover.getInfo());
    }

    @Test
    public void shouldNotMoveForwardWhenDead() {
        Rover rover = new Rover(1, 2, 2, Direction.S);
        rover.setDead(true);
        rover.setInfo("Already dead");
        Plateau plateau = createPlateau(rover);

        roverController.moveForward(rover, plateau);

        assertEquals("2 2 S", rover.getPosition());
        assertTrue(rover.isDead());
        assertEquals("Already dead" , rover.getInfo());
    }

    @Test
    public void shouldGoOffPlateau() {
        Rover rover = new Rover(1, 3, 2, Direction.E);
        Plateau plateau = createPlateau(rover);

        roverController.moveForward(rover, plateau);

        assertEquals("4 2 E", rover.getPosition());
        assertTrue(rover.isDead());
        assertEquals("Rover1 has gone off plateau. Last seen on position 3 2 E" , rover.getInfo());
    }

    @Test
    public void shouldCrash() {
        Rover rover1 = new Rover(1, 3, 2, Direction.N);
        Rover rover2 = new Rover(2, 3, 3, Direction.W);
        Plateau plateau = createPlateau(rover1, rover2);

        roverController.moveForward(rover1, plateau);

        assertEquals("3 3 N", rover1.getPosition());
        assertTrue(rover1.isDead());
        assertEquals("Rover1 has crashed Rover2 on position 3 3 N" , rover1.getInfo());
        assertEquals("3 3 W", rover2.getPosition());
        assertTrue(rover2.isDead());
        assertEquals("Rover2 got hit by Rover1 while standing on position 3 3 W" , rover2.getInfo());
    }

    @Test
    public void shouldCrashDeadRovers() {
        Rover rover1 = new Rover(1, 2, 3, Direction.W);
        Rover rover2 = new Rover(2, 1, 3, Direction.S);
        rover2.setDead(true);
        rover2.setInfo("Rover2 already crashed");
        Rover rover3 = new Rover(3, 1, 3, Direction.E);
        rover3.setDead(true);
        rover3.setInfo("Rover3 already crashed");
        Plateau plateau = createPlateau(rover1, rover2, rover3);

        roverController.moveForward(rover1, plateau);

        assertEquals("1 3 W", rover1.getPosition());
        assertTrue(rover1.isDead());
        assertEquals("Rover1 has crashed Rover2, Rover3 on position 1 3 W" , rover1.getInfo());
        assertEquals("1 3 S", rover2.getPosition());
        assertTrue(rover2.isDead());
        assertEquals("Rover2 already crashed" , rover2.getInfo());
        assertEquals("1 3 E", rover3.getPosition());
        assertTrue(rover3.isDead());
        assertEquals("Rover3 already crashed" , rover3.getInfo());
    }

    private Plateau createPlateau(Rover... rovers) {
        Plateau plateau = new Plateau();
        plateau.setLength(3);
        plateau.setWidth(3);
        for (Rover rover : rovers) {
            plateau.addRover(rover);
        }
        return plateau;
    }
}