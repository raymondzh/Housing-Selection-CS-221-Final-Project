package persistence;

import model.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StudentReaderTest {
    public ArrayList<Residence> res = new ArrayList<Residence>();

    @Test
    void testReader() {
        StudentReader r = new StudentReader();
    }

    @Test
    void testIOException() {
        try {
            StudentReader.readStudents(new File("./path/DNE"), res);
        } catch (IOException e){
            //expected
        }
    }
}
