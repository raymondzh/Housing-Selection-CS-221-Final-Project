package model;

import persistence.Saveable;
import persistence.StudentReader;

import java.io.PrintWriter;
import java.util.ArrayList;

// Student with name and ranked choices
public class Student implements Saveable {
    public String name;
    public ArrayList<Residence> choices;
    public boolean assigned = false;


    // REQUIRES: All residence choices must be ranked in the list
    public Student(String name, ArrayList<Residence> choices) {
        this.name = name;
        this.choices = choices;
    }

    // REQUIRES: student has >= 2 residences in their choices
    // MODIFIES: this, choices.get(1), choices.get(2)
    // EFFECTS: Removes the first choice in the students ranked choices, and removes student from that assignment.
    // Reassigns student to their new first choice.
    public void moveDownChoices() {
        choices.get(0).removeAssignment(this);
        choices.remove(0);
        choices.get(0).makeAssignment(this);

    }

    public Residence getFirstChoice() {
        return choices.get(0);
    }

    public boolean isAssigned() {
        return assigned;
    }

    public String getName() {
        return name;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public ArrayList<Residence> getChoices() {
        return choices;
    }

    // EFFECTS: writes the data of the student to a txt file
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(StudentReader.DELIMITER);
        for (Residence r : choices) {
            printWriter.print(r.getName());
            printWriter.print(StudentReader.DELIMITER);
        }
        printWriter.println();
    }
}
