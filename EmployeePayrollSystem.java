import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class Employee {
    protected int id;
    protected String name;
    protected double basicSalary;

    public Employee(int id, String name, double basicSalary) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public abstract double calculateAllowance();

    public double calculateGrossSalary() {
        return basicSalary + calculateAllowance();
    }

    public double calculateTax() {
        double gross = calculateGrossSalary();

        if (gross <= 30000) {
            return gross * 0.05;
        } else if (gross <= 60000) {
            return gross * 0.10;
        } else {
            return gross * 0.15;
        }
    }

    public double calculateNetSalary() {
        return calculateGrossSalary() - calculateTax();
    }

    public abstract String getEmployeeType();

    public void generateSalarySlip() {
        System.out.println("\n=========== Salary Slip ===========");
        System.out.println("Employee Type   : " + getEmployeeType());
        System.out.println("Employee ID     : " + id);
        System.out.println("Employee Name   : " + name);
        System.out.println("Basic Salary    : " + basicSalary);
        System.out.println("Allowance       : " + calculateAllowance());
        System.out.println("Gross Salary    : " + calculateGrossSalary());
        System.out.println("Tax Deduction   : " + calculateTax());
        System.out.println("Net Salary      : " + calculateNetSalary());
        System.out.println("===================================");
    }
}

class Manager extends Employee {
    public Manager(int id, String name, double basicSalary) {
        super(id, name, basicSalary);
    }

    @Override
    public double calculateAllowance() {
        return basicSalary * 0.20;
    }

    @Override
    public String getEmployeeType() {
        return "Manager";
    }
}

class Developer extends Employee {
    public Developer(int id, String name, double basicSalary) {
        super(id, name, basicSalary);
    }

    @Override
    public double calculateAllowance() {
        return basicSalary * 0.10;
    }

    @Override
    public String getEmployeeType() {
        return "Developer";
    }
}

public class EmployeePayrollSystem {
    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Employee Payroll System =====");
            System.out.println("1. Add Manager");
            System.out.println("2. Add Developer");
            System.out.println("3. Display All Employees");
            System.out.println("4. Generate Salary Slip by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    addEmployee("Manager");
                    break;
                case 2:
                    addEmployee("Developer");
                    break;
                case 3:
                    displayAllEmployees();
                    break;
                case 4:
                    generateSlipById();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Enter between 1 and 5.");
            }

        } while (choice != 5);
    }

    public static void addEmployee(String type) {
        System.out.print("Enter Employee ID: ");
        int id = getIntInput();

        if (findEmployeeById(id) != null) {
            System.out.println("Employee with this ID already exists.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        double salary = getDoubleInput();

        if (salary <= 0) {
            System.out.println("Salary must be greater than 0.");
            return;
        }

        Employee emp;
        if (type.equals("Manager")) {
            emp = new Manager(id, name, salary);
        } else {
            emp = new Developer(id, name, salary);
        }

        employees.add(emp);
        System.out.println(type + " added successfully.");
    }

    public static void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\n========= Employee Details =========");
        for (Employee emp : employees) {
            System.out.println("ID            : " + emp.getId());
            System.out.println("Name          : " + emp.getName());
            System.out.println("Type          : " + emp.getEmployeeType());
            System.out.println("Basic Salary  : " + emp.getBasicSalary());
            System.out.println("-----------------------------------");
        }
    }

    public static void generateSlipById() {
        System.out.print("Enter Employee ID: ");
        int id = getIntInput();

        Employee emp = findEmployeeById(id);

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        emp.generateSalarySlip();
    }

    public static Employee findEmployeeById(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
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
                System.out.print("Invalid input. Enter a valid salary: ");
                sc.next();
            }
        }
    }
}
