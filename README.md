# My Personal Project

## Residence Assignment

User stories:

- As a user, I want to be able to add new students too the list as more register
- As a user, I want to be able to let students order their preferences and give them the highest preference possible
- As a user, I want to be able to add new rooms to the algorithm as more rooms open up
- As a user, I want to be able to sort students into residences based on their ranked choices
- As a user, I want to be able to save my list of students and number of residences
- As a user, I want to be able to reopen my program and load my old saved list of students and residences

Given a list of students with residence applications, and a list of open rooms, create a set of assignments that make 
students as happy as possible. This application will be predominantly used by school administration to streamline the 
process of handing out residence slots. 


When started, the program will give you the option to load a save or start with default options, after that, it will 
display a menu that allows you to keep adding students and residences, as well as to save all entries and assign students.
Once students are assigned, the program displays the assignments, and deletes the save.

When adding slots, first select the residence to add slots to, then type in an amount of slots to add.

When adding students, first name the students, then choose their preferences in order.

Phase 4: Task 2 - There is a bidirectional association between Residence and Student.

Phase 4: Task 3
- Too much coupling in add students method in ResidenceAssignment, so a new method was created to deal with all three residence buttons.
- Constantly used the same set of lines to clear text - very high cohesion, so a method was created to fix that.