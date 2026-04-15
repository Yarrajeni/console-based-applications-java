import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class BankAccount {
    private int accountNumber;
    private String name;
    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + balance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount);
        System.out.println("Amount deposited successfully.");
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance. Withdrawal failed.");
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrawn: " + amount);
        System.out.println("Amount withdrawn successfully.");
        return true;
    }

    public boolean transfer(BankAccount receiver, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance. Transfer failed.");
            return false;
        }
        balance -= amount;
        receiver.balance += amount;

        transactionHistory.add("Transferred " + amount + " to Account No: " + receiver.accountNumber);
        receiver.transactionHistory.add("Received " + amount + " from Account No: " + this.accountNumber);

        System.out.println("Amount transferred successfully.");
        return true;
    }

    public void displayDetails() {
        System.out.println("\n----- Account Details -----");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + name);
        System.out.println("Balance        : " + balance);
    }

    public void showTransactionHistory() {
        System.out.println("\n--- Transaction History for Account " + accountNumber + " ---");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class BankAccountSimulation {
    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Bank Account Simulation =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Account Details");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    viewAccountDetails();
                    break;
                case 5:
                    transferMoney();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter between 1 and 7.");
            }

        } while (choice != 7);
    }

    public static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = getIntInput();

        if (findAccount(accountNumber) != null) {
            System.out.println("Account already exists with this number.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = getDoubleInput();

        if (balance < 0) {
            System.out.println("Initial balance cannot be negative.");
            return;
        }

        accounts.add(new BankAccount(accountNumber, name, balance));
        System.out.println("Account created successfully.");
    }

    public static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = getIntInput();

        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0.");
            return;
        }

        acc.deposit(amount);
    }

    public static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = getIntInput();

        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0.");
            return;
        }

        acc.withdraw(amount);
    }

    public static void viewAccountDetails() {
        System.out.print("Enter Account Number: ");
        int accountNumber = getIntInput();

        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        acc.displayDetails();
    }

    public static void transferMoney() {
        System.out.print("Enter Sender Account Number: ");
        int senderAccNo = getIntInput();

        BankAccount sender = findAccount(senderAccNo);
        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        System.out.print("Enter Receiver Account Number: ");
        int receiverAccNo = getIntInput();

        BankAccount receiver = findAccount(receiverAccNo);
        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (senderAccNo == receiverAccNo) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than 0.");
            return;
        }

        sender.transfer(receiver, amount);
    }

    public static void viewTransactionHistory() {
        System.out.print("Enter Account Number: ");
        int accountNumber = getIntInput();

        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        acc.showTransactionHistory();
    }

    public static BankAccount findAccount(int accountNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
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
