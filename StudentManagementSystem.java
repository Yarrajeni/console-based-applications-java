import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    private int id;
    private String name;
    private double[] marks;

    public Student(int id, String name, double[] marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double[] marks) {
        this.marks = marks;
    }

    public double calculateAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / marks.length;
    }

    public String calculateGrade() {
        double avg = calculateAverage();

        if (avg >= 90) {
            return "A+";
        } else if (avg >= 80) {
            return "A";
        } else if (avg >= 70) {
            return "B";
        } else if (avg >= 60) {
            return "C";
        } else if (avg >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayStudent() {
        System.out.println("-----------------------------------");
        System.out.println("Student ID   : " + id);
        System.out.println("Student Name : " + name);
        System.out.print("Marks        : ");
        for (double mark : marks) {
            System.out.print(mark + " ");
        }
        System.out.println();
        System.out.printf("Average      : %.2f%n", calculateAverage());
        System.out.println("Grade        : " + calculateGrade());
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add New Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student Record");
            System.out.println("5. Delete Student Record");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter between 1 and 6.");
            }

        } while (choice != 6);
    }

    public static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = getIntInput();

        if (findStudentById(id) != null) {
            System.out.println("Student with this ID already exists.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter number of subjects: ");
        int subjectCount = getIntInput();

        if (subjectCount <= 0) {
            System.out.println("Number of subjects must be greater than 0.");
            return;
        }

        double[] marks = new double[subjectCount];

        for (int i = 0; i < subjectCount; i++) {
            while (true) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                double mark = getDoubleInput();

                if (mark >= 0 && mark <= 100) {
                    marks[i] = mark;
                    break;
                } else {
                    System.out.println("Marks should be between 0 and 100.");
                }
            }
        }

        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    public static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.displayStudent();
        }
    }

    public static void searchStudentById() {
        System.out.print("Enter Student ID to search: ");
        int id = getIntInput();

        Student s = findStudentById(id);

        if (s != null) {
            s.displayStudent();
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = getIntInput();

        Student s = findStudentById(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter new name: ");
        String newName = sc.nextLine();

        System.out.print("Enter new number of subjects: ");
        int subjectCount = getIntInput();

        if (subjectCount <= 0) {
            System.out.println("Number of subjects must be greater than 0.");
            return;
        }

        double[] newMarks = new double[subjectCount];

        for (int i = 0; i < subjectCount; i++) {
            while (true) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                double mark = getDoubleInput();

                if (mark >= 0 && mark <= 100) {
                    newMarks[i] = mark;
                    break;
                } else {
                    System.out.println("Marks should be between 0 and 100.");
                }
            }
        }

        s.setName(newName);
        s.setMarks(newMarks);

        System.out.println("Student record updated successfully.");
    }

    public static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = getIntInput();

        Student s = findStudentById(id);

        if (s != null) {
            students.remove(s);
            System.out.println("Student record deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public static int getIntInput() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter an integer: ");
                sc.next();
            }
        }
    }

    public static double getDoubleInput() {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a valid number: ");
                sc.next();
            }
        }
    }
}
