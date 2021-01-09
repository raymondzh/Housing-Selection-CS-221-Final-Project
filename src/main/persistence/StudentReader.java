package persistence;

import model.Residence;
import model.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read student data from a file
public class StudentReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of students parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static ArrayList<Student> readStudents(File file, ArrayList<Residence> residences) throws IOException {
        List<String> fileContent = readFile(file);
        return parseStudents(fileContent, residences);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of students parsed from list of strings
    // where each string contains data for one student
    private static ArrayList<Student> parseStudents(List<String> fileContent, ArrayList<Residence> residences) {
        ArrayList<Student> students = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            students.add(parseStudent(lineComponents, residences));
        }

        return students;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size residences + 1 where element 0 represents the
    // name of the student, and the remaining elements represent the ordered choices of the student
    // EFFECTS: returns an student constructed from components
    private static Student parseStudent(List<String> components, ArrayList<Residence> residences) {
        String name;
        name = components.get(0);
        String res;
        ArrayList<Residence> choices = new ArrayList<>();
        for (int i = 0; i < residences.size(); i++) {
            res = components.get(i + 1);
            for (Residence r : residences) {
                if (r.getName().equals(res)) {
                    choices.add(r);
                }
            }
        }

        return new Student(name, choices);
    }
}
