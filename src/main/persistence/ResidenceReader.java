package persistence;

import model.Residence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read residence data from a file
public class ResidenceReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of residences parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static ArrayList<Residence> readResidence(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseResidences(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of residences parsed from list of strings
    // where each string contains data for one residence
    private static ArrayList<Residence> parseResidences(List<String> fileContent) {
//        Residence vanier = new Residence("Vanier", 0);
//        Residence totem = new Residence("Totem", 0);
//        Residence orchard = new Residence("Orchard", 0);
        ArrayList<Residence> residences = new ArrayList<>();
//        residences.add(vanier);
//        residences.add(totem);
//        residences.add(orchard);
        Residence temp;

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            temp = parseResidence(lineComponents);
            for (Residence r : residences) {
                if (r.getName().equals(temp.getName())) {
                    r.addSlots(temp.getSlots());
                }
            }
        }

        return residences;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 1 where element 0 represents the
    // name of the residences, element 1 represents number of slots in the residence
    // EFFECTS: returns an residence constructed from components
    private static Residence parseResidence(List<String> components) {
        String name = components.get(0);
        int slots = Integer.parseInt(components.get(1));

        return new Residence(name, slots);
    }
}
