package persistence;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ResidenceReaderTest {
    @Test
    void testReader() {
        ResidenceReader r = new ResidenceReader();
    }

    @Test
    void testIOException() {
        try {
            ResidenceReader.readResidence(new File("./path/DNE"));
        } catch (IOException e){
            //expected
        }
    }
}
