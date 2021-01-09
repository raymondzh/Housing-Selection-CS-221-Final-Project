package ui;

import model.Residence;
import model.Student;
import persistence.ResidenceReader;
import persistence.StudentReader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Residence assignment application
public class ResidenceAssignment extends JFrame implements ActionListener {

    private static final String RESIDENCE_FILE = "./data/residence.txt";
    private static final String STUDENT_FILE = "./data/student.txt";
    ArrayList<Student> students = new ArrayList<>();
    Residence vanier;
    Residence totem;
    Residence orchard;
    ArrayList<Residence> residences = new ArrayList<>();
    final Scanner in;

    // MODIFIES: this
    // EFFECTS: processes user input
    public ResidenceAssignment() {
        super("Residence Assigner");
        in = new Scanner(System.in);
        initializeGraphics();
        loadState();
    }


    public void actionPerformed(ActionEvent e) {

    }

    //MODIFIES: this
    //EFFECTS: creates frame for gui
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BorderLayout()); tf does this do?
        setPreferredSize(new Dimension(400, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: adds a new student into the students
    public void addStudent() {
        clearWindow();
        ArrayList<Residence> choices = new ArrayList<>();

        JLabel label1 = new JLabel("What is the students name?");
        add(label1);
        JTextField field = new JTextField(10);
        add(field);
        JButton enter = new JButton("Enter");
        add(enter);
        pack();
        enter.addActionListener((ActionEvent e) -> {
            studentNameSelected(label1, field, enter, choices);
        });

    }

    public void studentNameSelected(JLabel label1, JTextField field, JButton enter, ArrayList<Residence> choices) {
        label1.setText("What residence would you like to rank first");
        String name = field.getText();
        remove(field);
        remove(enter);
        JButton vanier = new JButton("Vanier");
        vanier.addActionListener((ActionEvent f) -> {
            residenceSelectionButton(this.vanier, name, vanier, label1, choices);
        });
        add(vanier);
        JButton totem = new JButton("Totem");
        totem.addActionListener((ActionEvent f) -> {
            residenceSelectionButton(this.totem, name, totem, label1, choices);
        });
        add(totem);
        JButton orchard = new JButton("Orchard");
        orchard.addActionListener((ActionEvent f) -> {
            residenceSelectionButton(this.orchard, name, orchard, label1, choices);
        });
        add(orchard);
    }

    public void residenceSelectionButton(Residence res, String name, JButton button,
                                              JLabel label1, ArrayList<Residence> choices) {
        choices.add(res);
        remove(button);
        pack();
        label1.setText("What residence would you like to rank next");
        if (choices.size() == residences.size()) {
            students.add(new Student(name, choices));
            displayMenu();
        }
    }

    // REQUIRES: A residence
    // EFFECTS: Adds a button that allows the user to add slots to that residence.
    public void addSlotsToResidenceButton(Residence r) {
        JButton button = new JButton(r.getName());
        button.addActionListener((ActionEvent e) -> {
            clearWindow();
            JLabel label = new JLabel("How many slots would you like to add?");
            add(label);
            JTextField field = new JTextField(10);
            add(field);
            JButton enter = new JButton("Enter");
            enter.addActionListener((ActionEvent f) -> {
                try {
                    int amount = Integer.parseInt(field.getText());
                    r.addSlots(amount);
                    displayMenu();
                } catch (NumberFormatException g) {
                    label.setText("That is not a valid input");
                    pack();
                }
            });
            add(enter);
            pack();
        });
        add(button);
    }

    // MODIFIES: this
    // EFFECTS: adds a number of slots to a residence that the use selects
    public void addSlots() {
        clearWindow();
        JLabel label;
        label = new JLabel("Which residence would you like to add slots too?");
        add(label);
        for (Residence r :residences) {
            addSlotsToResidenceButton(r);
        }
        pack();
    }

    // EFFECTS: saves states of students and residences
    public void saveState() {
        JLabel label = new JLabel();
        try {
            Writer residenceWriter = new Writer(new File(RESIDENCE_FILE));
            Writer studentWriter = new Writer(new File(STUDENT_FILE));
            for (Residence r : residences) {
                residenceWriter.write(r);
            }
            residenceWriter.close();
            for (Student s : students) {
                studentWriter.write(s);
            }
            studentWriter.close();
            label.setText("Accounts saved to file " + RESIDENCE_FILE + " and " + STUDENT_FILE);
        } catch (FileNotFoundException e) {
            label.setText("Unable to save accounts to " + RESIDENCE_FILE + " and " + STUDENT_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        } finally {
            add(label);
            pack();
        }
    }

    // MODIFIES: this
    // EFFECTS: user chooses between loading the state from saved data or default data
    private void loadState() {

        JButton defaultStartBtn = new JButton("Start program with default entries");
        defaultStartBtn.setActionCommand("defaultStart");
        defaultStartBtn.addActionListener((ActionEvent e) -> {
            makeResidences();
            makeStudents();
            displayMenu();
        });
        JButton loadSaveBtn = new JButton("Load from last save");
        loadSaveBtn.setActionCommand("loadSave");
        loadSaveBtn.addActionListener((ActionEvent e) -> {
            try {
                residences = ResidenceReader.readResidence(new File(RESIDENCE_FILE));
                students = StudentReader.readStudents(new File(STUDENT_FILE), residences);
            } catch (IOException i) {
                makeResidences();
                makeStudents();
            } finally {
                displayMenu();
            }
        });

        add(defaultStartBtn);
        add(loadSaveBtn);

    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        clearWindow();
        int slots = 0;
        for (Residence r : residences) {
            slots += r.getSlots();
        }
        add(new JLabel("You currently have " + slots + " slots, \r\n Vanier: " + vanier.getSlots()
                + "\r\n Totem: " + totem.getSlots() + "\r\n Orchard: " + orchard.getSlots() + "\r\n "));
        add(new JLabel("                                                  And " + students.size()
                + " students.                                                  "));

        addMenuButtons();

    }

    //EFFECTS: adds button to exit program
    public void addExitButton() {
        JButton exitProgram = new JButton("Exit Program");
        exitProgram.setActionCommand("exitProgram");
        exitProgram.addActionListener((ActionEvent e) -> dispose());
        add(exitProgram);
    }

    //EFFECTS: adds button to add slots
    public void addAddSlotsButton() {
        JButton addSlots = new JButton("Add Slots");
        addSlots.setActionCommand("addSlots");
        addSlots.addActionListener((ActionEvent e) -> addSlots());
        add(addSlots);
    }

    //EFFECTS: adds button to add students
    public void addAddStudentsButton() {
        JButton addStudent = new JButton("Add a Student");
        addStudent.setActionCommand("addStudent");
        addStudent.addActionListener((ActionEvent e) -> addStudent());
        add(addStudent);
    }

    //EFFECTS: adds button to assign students
    public void addAssignStudentsButton() {
        JButton assignStudents = new JButton("Assign Students");
        assignStudents.setActionCommand("assignStudents");
        assignStudents.addActionListener((ActionEvent e) -> {
            int slots = 0;
            for (Residence r : residences) {
                slots += r.getSlots();
            }
            if (slots == students.size()) {
                makeInitialAssignments();
                assignmentAlgorithm();
                printResults();
            } else {
                add(new JLabel("You must have the same amount of students and slots"));
                pack();
            }
        });
        add(assignStudents);
    }

    //EFFECTS: adds button to save program
    public void addSaveStateButton() {
        JButton saveState = new JButton("Save students and residences");
        saveState.setActionCommand("saveState");
        saveState.addActionListener((ActionEvent e) -> saveState());
        add(saveState);
    }

    //EFFECTS: adds all menu buttons
    public void addMenuButtons() {
        addExitButton();
        addAddSlotsButton();
        addAddStudentsButton();
        addAssignStudentsButton();
        addSaveStateButton();
        pack();
    }

    // EFFECTS: prints the results of the algorithm to the user
    public void printResults() {
        clearWindow();
        try {
            Writer residenceWriter = new Writer(new File(RESIDENCE_FILE));
            Writer studentWriter = new Writer(new File(STUDENT_FILE));
            studentWriter.close();
            residenceWriter.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("File not found");
        }
        for (Residence r: residences) {
            add(new JLabel("In " + r.getName() + ": "));
            for (int i = 0; i < r.getSlots(); i++) {
                add(new JLabel(r.getAssignments().get(i).getName() + ", "));
            }
            pack();
        }
        JButton exit = new JButton("Exit");
        exit.addActionListener((ActionEvent e) -> dispose());
        add(exit);
        pack();

    }

    // REQUIRES: number of students <= number of slots
    // MODIFIES: this
    // EFFECTS: moves students assignments down their list of choices based on when they were added, until assignments
    // are valid
    public void assignmentAlgorithm() {
        while (!checkAllAssignmentsValid()) {
            for (Residence r : residences) {
                if (!r.checkAssignmentsValid()) {
                    for (Student s : r.getAssignments()) {
                        if (s.getChoices().size() >= 2) {
                            r.getAssignments().get(0).moveDownChoices();
                            break;
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a hardcoded list of residences
    public void makeResidences() {
        vanier = new Residence("Vanier", 2);
        totem = new Residence("Totem", 1);
        orchard = new Residence("Orchard", 3);
        residences.add(vanier);
        residences.add(totem);
        residences.add(orchard);
    }

    // MODIFIES: this
    // EFFECTS: assigns all students to their first choice
    public void makeInitialAssignments() {
        for (Student s : students) {
            s.getFirstChoice().makeAssignment(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a hardcoded list of students
    public void makeStudents() {
        Student alex = new Student("Alex", new ArrayList<>(Arrays.asList(totem, vanier, orchard)));
        Student billy = new Student("Bill", new ArrayList<>(Arrays.asList(orchard, totem, vanier))); //2
        Student chris = new Student("Chris", new ArrayList<>(Arrays.asList(orchard, vanier, totem))); //2
        Student david = new Student("David", new ArrayList<>(Arrays.asList(totem, orchard, vanier))); //2
        Student emily = new Student("Emily", new ArrayList<>(Arrays.asList(orchard, vanier, totem))); //1
        Student frank = new Student("Frank", new ArrayList<>(Arrays.asList(orchard, totem, vanier))); //1
        students.add(alex);
        students.add(billy);
        students.add(chris);
        students.add(david);
        students.add(emily);
        students.add(frank);
    }

    // EFFECTS: returns true if all residences are not over-assigned
    public boolean checkAllAssignmentsValid() {
        for (Residence r : residences) {
            if (!r.checkAssignmentsValid()) {
                return false;
            }
        }
        return true;
    }


    // EFFECTS: Clears all Swing elements.
    public void clearWindow() {
        getContentPane().removeAll();
        getContentPane().repaint();
    }

}
