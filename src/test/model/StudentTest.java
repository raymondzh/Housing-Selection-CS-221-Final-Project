package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private Residence orchard;
    private Residence vanier;
    private Student s1;


    @BeforeEach
    void runBefore() {
        orchard = new Residence("Orchard", 3);
        vanier = new Residence("Vanier", 1);
        s1 = new Student("Alex", new ArrayList<>(Arrays.asList(vanier, orchard)));
    }

    @Test
    void testGetName() {
        assertEquals("Alex", s1.getName());
    }

    @Test
    void testGetFirstChoice() {
        assertEquals(vanier, s1.getFirstChoice());
    }

    @Test
    void testMoveDownChoices() {
        vanier.makeAssignment(s1);
        assertEquals(2, s1.getChoices().size());
        s1.moveDownChoices();
        assertEquals(orchard, s1.getFirstChoice());
        assertEquals(1, s1.getChoices().size());
        assertEquals(s1, orchard.getAssignments().get(0));
        assertEquals(0, vanier.getAssignments().size());
    }

}
