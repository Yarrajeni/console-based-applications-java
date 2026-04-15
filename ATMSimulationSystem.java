import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class ATMAccount {
    private int accountNumber;
    private String accountHolder;
    private int pin;
    private double balance;
    private double dailyWithdrawn;
    private final double DAILY_LIMIT = 10000;
    private ArrayList<String> miniStatement;

    public ATMAccount(int accountNumber, String accountHolder, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = balance;
        this.dailyWithdrawn = 0;
        this.miniStatement = new ArrayList<>();
        miniStatement.add("Account created with balance: " + balance);
    }

    public boolean validatePin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void deposit(double amount) {
        balance += amount;
        miniStatement.add("Deposited: " + amount);
        System.out.println("Amount deposited successfully.");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else if (dailyWithdrawn + amount > DAILY_LIMIT) {
            System.out.println("Daily withdrawal limit exceeded.");
        } else {
            balance -= amount;
            dailyWithdrawn += amount;
            miniStatement.add("Withdrawn: " + amount);
            System.out.println("Amount withdrawn successfully.");
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    public void showMiniStatement() {
        System.out.println("\n===== Mini Statement =====");
        for (String transaction : miniStatement) {
            System.out.println(transaction);
        }
    }

    public void showAccountDetails() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : " + balance);
        System.out.println("Withdrawn Today: " + dailyWithdrawn);
        System.out.println("Daily Limit    : " + DAILY_LIMIT);
    }
}

public class ATMSimulationSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ATMAccount user = new ATMAccount(12345, "Bharath", 1234, 25000);

        int attempts = 0;
        boolean authenticated = false;

        System.out.println("===== Welcome to ATM =====");

        while (attempts < 3) {
            System.out.print("Enter your 4-digit PIN: ");
            int enteredPin = getIntInput();

            if (user.validatePin(enteredPin)) {
                authenticated = true;
                break;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts left: " + (3 - attempts));
            }
        }

        if (!authenticated) {
            System.out.println("Too many incorrect attempts. Account access blocked.");
            return;
        }

        int choice;
        do {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Balance Check");
            System.out.println("4. Mini Statement");
            System.out.println("5. Account Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = getDoubleInput();
                    if (withdrawAmount <= 0) {
                        System.out.println("Enter a valid amount.");
                    } else {
                        user.withdraw(withdrawAmount);
                    }
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = getDoubleInput();
                    if (depositAmount <= 0) {
                        System.out.println("Enter a valid amount.");
                    } else {
                        user.deposit(depositAmount);
                    }
                    break;

                case 3:
                    user.checkBalance();
                    break;

                case 4:
                    user.showMiniStatement();
                    break;

                case 5:
                    user.showAccountDetails();
                    break;

                case 6:
                    System.out.println("Thank you for using ATM.");
                    break;

                default:
                    System.out.println("Invalid choice. Enter between 1 and 6.");
            }

        } while (choice != 6);
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
