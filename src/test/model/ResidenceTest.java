package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ResidenceTest {
    private Residence orchard;
    private Residence vanier;
    private Student s1;



    @BeforeEach
    void runBefore() {
        orchard = new Residence("Orchard", 3);
        vanier = new Residence("Vanier", 1);
        s1= new Student("Alex", new ArrayList<>(Arrays.asList(vanier, orchard)));
    }

    @Test
    void testMakeAssignment() {
        orchard.makeAssignment(s1);
        assertEquals(s1, orchard.getAssignments().get(0));
        assertTrue(s1.isAssigned());
    }

    @Test
    void testCheckAssignmentsValid() {
        assertTrue(vanier.checkAssignmentsValid());
        vanier.makeAssignment(s1);
        assertTrue(vanier.checkAssignmentsValid());
        vanier.makeAssignment(s1);
        assertFalse(vanier.checkAssignmentsValid());
    }

    @Test
    void testRemoveAssignment() {
        vanier.makeAssignment(s1);
        assertTrue(vanier.getAssignments().contains(s1));
        vanier.removeAssignment(s1);
        assertFalse(vanier.getAssignments().contains(s1));
        assertFalse(s1.isAssigned());
    }

    @Test
    void testGetSlots() {
        assertEquals(1, vanier.getSlots());
    }

    @Test
    void testAddSlots() {
        vanier.addSlots(1);
        assertEquals(2, vanier.getSlots());
    }

    @Test
    void testGetName() {
        assertEquals("Vanier", vanier.getName());
    }


}