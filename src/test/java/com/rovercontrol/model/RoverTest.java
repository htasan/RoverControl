package com.rovercontrol.model;

import com.rovercontrol.enums.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoverTest {

    @Test
    public void shouldGetName() {
        Rover rover = new Rover(3, 0, 0, Direction.N);
        assertEquals("Rover3", rover.getName());
    }

    @Test
    public void shouldGetPosition() {
        Rover rover = new Rover(3, 3, 2, Direction.E);
        assertEquals("3 2 E", rover.getPosition());
    }

    @Test
    public void shouldGetPreviousPosition() {
        Rover rover = new Rover(3, 3, 2, Direction.E);
        assertEquals("2 2 E", rover.getPreviousPosition());
    }

    @Test
    public void shouldGetOnSamePosition() {
        Rover rover1 = new Rover(1, 3, 2, Direction.W);
        Rover rover2 = new Rover(2, 3, 2, Direction.E);
        Rover rover3 = new Rover(3, 3, 1, Direction.W);
        assertTrue(rover1.onSamePosition(rover2));
        assertFalse(rover1.onSamePosition(rover3));
    }

    @Test
    public void shouldGetIsInPlateau() {
        Rover rover1 = new Rover(1, 3, 2, Direction.W);
        assertTrue(rover1.isInPlateau(3, 2));
        assertFalse(rover1.isInPlateau(2, 2));
        assertFalse(rover1.isInPlateau(3, 1));
        Rover rover2 = new Rover(1, -1, 2, Direction.W);
        assertFalse(rover2.isInPlateau(3, 1));
        Rover rover3 = new Rover(1, 3, -1, Direction.W);
        assertFalse(rover3.isInPlateau(3, 3));
    }
}