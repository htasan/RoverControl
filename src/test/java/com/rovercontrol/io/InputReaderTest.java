package com.rovercontrol.io;

import com.rovercontrol.exception.InputRuleException;
import com.rovercontrol.model.Plateau;
import com.rovercontrol.model.Rover;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InputReaderTest {

    @Test
    public void shouldThrowExceptionWhenInputFileNotFound() {
        try {
            new InputReader().createPlateau("test_input_.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("test_input_.txt could not be found in resources", ire.getMessage());
        }
    }

    @Test
    public void shouldCreatePlateau() throws InputRuleException {
        Plateau plateau = new InputReader().createPlateau("test_input_0.txt");
        List<Rover> roverList = plateau.getRoverList();
        assertEquals(5, plateau.getWidth());
        assertEquals(5, plateau.getLength());
        assertEquals(2, roverList.size());
        assertEquals("Rover1 is on position 1 2 N", roverList.get(0).getInfo());
        assertEquals("Rover2 is on position 3 3 E", roverList.get(1).getInfo());
    }

    @Test
    public void shouldThrowExceptionWhenPlateauPropertiesNotFound() {
        try {
            new InputReader().createPlateau("test_input_1.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Plateau properties could not be read", ire.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionForNegativePlateauDimension() {
        try {
            new InputReader().createPlateau("test_input_2.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Both dimensions of plateau should be positive", ire.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenRoverPositionIsWrong() {
        try {
            new InputReader().createPlateau("test_input_3.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Position info for Rover2 could not be read", ire.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenRoverPositionIsOutOfPlateau() {
        try {
            new InputReader().createPlateau("test_input_4.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Rover1 cannot be positioned on 1 6 N, which is out of plateau", ire.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenRoversAreOnSamePosition() {
        try {
            new InputReader().createPlateau("test_input_5.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Rover2 cannot be positioned on 1 2 E because there is another rover there", ire.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenCommandInfoIsWrong() {
        try {
            new InputReader().createPlateau("test_input_6.txt");
            fail();
        } catch (InputRuleException ire) {
            assertEquals("Command info for Rover1 could not be read", ire.getMessage());
        }
    }
}