import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String toFileString() {
        return name + "," + phone + "," + email;
    }

    public void display() {
        System.out.println("----------------------------------");
        System.out.println("Name  : " + name);
        System.out.println("Phone : " + phone);
        System.out.println("Email : " + email);
    }
}

public class ContactManagementSystem {
    static final String FILE_NAME = "contacts.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Contact Management System =====");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    deleteContact();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    displayAllContacts();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }

    public static void addContact() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(name + "," + phone + "," + email);
            bw.newLine();
            System.out.println("Contact added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static ArrayList<Contact> readContactsFromFile() {
        ArrayList<Contact> contacts = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return contacts;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return contacts;
    }

    public static void writeContactsToFile(ArrayList<Contact> contacts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                bw.write(c.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    public static void deleteContact() {
        System.out.print("Enter name to delete: ");
        String searchName = sc.nextLine().trim().toLowerCase();

        ArrayList<Contact> contacts = readContactsFromFile();
        boolean removed = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().toLowerCase().equals(searchName)) {
                contacts.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            writeContactsToFile(contacts);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public static void searchContact() {
        System.out.print("Enter name to search: ");
        String searchName = sc.nextLine().trim().toLowerCase();

        ArrayList<Contact> contacts = readContactsFromFile();
        boolean found = false;

        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(searchName)) {
                c.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    public static void displayAllContacts() {
        ArrayList<Contact> contacts = readContactsFromFile();

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\n===== All Contacts =====");
        for (Contact c : contacts) {
            c.display();
        }
    }
}
