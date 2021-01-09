package model;

import persistence.ResidenceReader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;

// Residence with name and list of students assigned
public class Residence implements Saveable {
    private final String name;
    private int slots;
    private ArrayList<Student> assignments = new ArrayList<>();

    // REQUIRES slots >= 0
    // EFFECTS: name of residence and slots available is set
    public Residence(String name, int slots) {
        this.name = name;
        this.slots = slots;
    }

    // REQUIRES: assignments.size <= slots
    // MODIFIES: this, student
    // EFFECTS: assigns one student to a slot, sets the student status to assigned
    public void makeAssignment(Student student) {
        assignments.add(student);
        student.setAssigned(true);
    }

    // EFFECTS: checks if there are less than or equal students than slots
    public boolean checkAssignmentsValid() {
        return assignments.size() <= slots;
    }

    // REQUIRES: Student is currently assigned to the residence
    // MODIFIES: this, student
    // EFFECTS: removes a student from the assignment list, sets student status as unassigned
    public void removeAssignment(Student student) {
        assignments.remove(student);
        student.setAssigned(false);
    }

    // MODIFIES: this
    // EFFECTS: adds a given amount of slots
    public void addSlots(int slots) {
        this.slots += slots;
    }

    public ArrayList<Student> getAssignments() {
        return assignments;
    }

    public int getSlots() {
        return slots;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: writes the data of a residence to a txt file
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(ResidenceReader.DELIMITER);
        printWriter.println(slots);
    }
}
