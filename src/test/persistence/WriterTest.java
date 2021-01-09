package persistence;

import model.Residence;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WriterTest {
    private static final String WRITER_TEST = "./data/writer_test.txt";
    private Writer testWriter;
    private Residence vanier;
    private Residence orchard;
    private Student alex;
    private ArrayList<Residence> residences = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(WRITER_TEST));
        orchard = new Residence("Orchard", 3);
        vanier = new Residence("Vanier", 1);
        residences.add(vanier);
        residences.add(orchard);
        alex = new Student("Alex", new ArrayList<Residence>(Arrays.asList(orchard, vanier)));
    }

    @Test
    void testWriteStudent() {
        testWriter.write(alex);
        testWriter.close();

        try {
            students = StudentReader.readStudents(new File(WRITER_TEST), residences);
            assertEquals(1, students.size());
            assertEquals("Alex", students.get(0).getName());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteResidence() {
        testWriter.write(orchard);
        testWriter.close();

        try {
            residences = ResidenceReader.readResidence(new File(WRITER_TEST));
            assertEquals(1, residences.size());
            assertEquals("Orchard", residences.get(0).getName());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException should not have been thrown");
        }
    }
}
